package com.tencent.hours.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.event.SyncReadListener;
import com.alibaba.fastjson.JSON;
import com.tencent.hours.common.CommonConstant;
import com.tencent.hours.common.UserType;
import com.tencent.hours.dto.DepWarnCountDto;
import com.tencent.hours.dto.EmpWarnDto;
import com.tencent.hours.dto.EmployeeLackDto;
import com.tencent.hours.entity.*;
import com.tencent.hours.repository.*;
import com.tencent.hours.service.WarnService;
import com.tencent.hours.util.LocalReadListener;
import com.tencent.hours.util.MailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Service
@Slf4j
public class WarnServiceImpl implements WarnService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HoursTempRepository hoursTempRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private WarnLogRepository warnLogRepository;
    @Autowired
    private CountryHolidaysRepository countryHolidaysRepository;
    @Autowired
    private MailSenderUtil mailSenderUtil;
    @Autowired
    private EmployeeLeaderRepository employeeLeaderRepository;

    private final String THRESHOLD_DEFAULT = "2";


    @PostConstruct
    public void initThreshold() {
        String thresholdStr = redisTemplate.opsForValue().get(CommonConstant.RedisKey.THRESHOLD_KEY);
        if (thresholdStr == null) {
            redisTemplate.opsForValue().set(CommonConstant.RedisKey.THRESHOLD_KEY, THRESHOLD_DEFAULT);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void uploadRoster(MultipartFile file, UserType userType) {
        try {
            if (UserType.EMPLOYEE.equals(userType)) {
                List<Employee> employeeList = EasyExcel.read(file.getInputStream(), Employee.class, new SyncReadListener()).sheet().doReadSync();
                if (!CollectionUtils.isEmpty(employeeList)) {
                    employeeRepository.deleteAll();
                    employeeRepository.saveAll(employeeList);
                }
            } else if (UserType.LEADER.equals(userType)) {
                List<EmployeeLeader> employeeLeaderList = EasyExcel.read(file.getInputStream(), EmployeeLeader.class, new SyncReadListener()).sheet().doReadSync();
                if (!CollectionUtils.isEmpty(employeeLeaderList)) {
                    employeeLeaderRepository.deleteAll();
                    employeeLeaderRepository.saveAll(employeeLeaderList);
                }
            }
        } catch (Exception e) {
            log.error("upload file failed !", e);
            throw new RuntimeException("upload file failed !");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void warningPrompt(MultipartFile file) {
        List<HoursTemp> hoursTempList;
        try {
            hoursTempList = EasyExcel.read(file.getInputStream(), HoursTemp.class, new SyncReadListener()).sheet().doReadSync();
        } catch (IOException e) {
            log.error("parse file failed !", e);
            throw new RuntimeException("parse file failed !");
        }
        if (!CollectionUtils.isEmpty(hoursTempList)) {
            hoursTempRepository.deleteAll();
            hoursTempRepository.saveAll(hoursTempList);
            List<Map<String, String>> lastSpendDateList = hoursTempRepository.findLastSpendDate();
            String lastSpendStr = JSON.toJSONString(lastSpendDateList);
            List<EmpWarnDto> empWarnDtoList = JSON.parseArray(lastSpendStr, EmpWarnDto.class);
            String thresholdStr = redisTemplate.opsForValue().get(CommonConstant.RedisKey.THRESHOLD_KEY);
            Integer threshold = Integer.valueOf(thresholdStr);
            if (threshold == null) {
                threshold = 1;
            }
            final Integer finalThreshold = threshold;
            if (!CollectionUtils.isEmpty(empWarnDtoList)) {
                List<EmpWarnDto> targetEmpWarnDtoList = empWarnDtoList.stream()
                        .filter(empWarnDto -> empWarnDto.getLastSpendDate() == null ||
                                (ChronoUnit.DAYS.between(empWarnDto.getLastSpendDate(), LocalDate.now()) > finalThreshold) &&
                                        compare(finalThreshold, empWarnDto)
                        )
                        .collect(Collectors.toList());

                targetEmpWarnDtoList.forEach(empWarnDto -> mailSenderUtil.sendEmp(empWarnDto, calLastWorkDate(LocalDate.now(), finalThreshold)));
                //工时补填更新
                clearRepair();
                List<WarnLog> warnLogList = warnLogRepository.findByLackDateAndSendStatus(LocalDate.now(), Boolean.FALSE);
                // TODO: 2020/11/16 删除临时数据

                if (!CollectionUtils.isEmpty(warnLogList)) {
                    throw new RuntimeException("邮件可能发送失败");
                }
            }
        }
    }

    private boolean compare(Integer finalThreshold, EmpWarnDto empWarnDto) {
        return calLastWorkDate(LocalDate.now(), finalThreshold).atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli() >
                empWarnDto.getLastSpendDate().atStartOfDay(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void clearRepair() {
        List<Map<String, Object>> supplementList = warnLogRepository.findSupplement();
        if (!CollectionUtils.isEmpty(supplementList)) {
            List<Long> idList = supplementList.stream().map(m -> Long.valueOf(m.get("id").toString())).collect(Collectors.toList());
            warnLogRepository.deleteAllByIdIn(idList);
        }
    }

    @Override
    public void setThreshold(Integer threshold) {
        redisTemplate.opsForValue().set(CommonConstant.RedisKey.THRESHOLD_KEY, threshold.toString());
    }

    @Override
    public Integer getThreshold() {
        return Integer.valueOf(redisTemplate.opsForValue().get(CommonConstant.RedisKey.THRESHOLD_KEY));
    }

    @Override
    public List<DepWarnCountDto> findWeekWarnGroupByEmp() {
        LocalDate monday = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate sunday = monday.plusDays(6);
        List<Map<String, Object>> mapList = warnLogRepository.findGroupByEmp(monday, sunday);
        if (!CollectionUtils.isEmpty(mapList)) {
            List<DepWarnCountDto> depWarnCountDtoList = JSON.parseArray(JSON.toJSONString(mapList), DepWarnCountDto.class);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DateFormat.DATE_FORMAT);
            String startDateStr = formatter.format(monday);
            String endDateStr = formatter.format(sunday);
            Integer number = 1;
            for (DepWarnCountDto dto : depWarnCountDtoList) {
                dto.setPeriod(startDateStr + "-" + endDateStr);
                dto.setNumber(number);
                number++;
            }
            return depWarnCountDtoList;
        }
        return null;
    }

    @Transactional
    @Override
    public void uploadCountryHoliday(MultipartFile file) {
        List<CountryHolidays> countryHolidaysList;
        try {
            countryHolidaysList = EasyExcel.read(file.getInputStream(), CountryHolidays.class, new LocalReadListener()).sheet().doReadSync();
        } catch (IOException e) {
            log.error("parse file failed !", e);
            throw new RuntimeException("parse file failed !");
        }
        if (!CollectionUtils.isEmpty(countryHolidaysList)) {
            countryHolidaysRepository.deleteAll();
            countryHolidaysRepository.saveAll(countryHolidaysList);
        }
    }

    @Override
    public List<EmployeeLackDto> findEmpList() {
        LocalDate monday = LocalDate.now().with(ChronoField.DAY_OF_WEEK, 1);
        LocalDate sunday = monday.plusDays(6);
        List<Map<String, Object>> mapList = warnLogRepository.findEmpListByLackDate(monday, sunday);
        if (!CollectionUtils.isEmpty(mapList)) {
            List<EmployeeLackDto> employeeLackDtoList = JSON.parseArray(JSON.toJSONString(mapList), EmployeeLackDto.class);
            return employeeLackDtoList;
        }
        return null;
    }

    /**
     * 获取上一个工作日期
     *
     * @param startDate 起始日期
     * @param interval  间隔天数
     * @return
     */
    private LocalDate calLastWorkDate(LocalDate startDate, Integer interval) {
        //循环遍历每个日期
        LocalDate lastDate = startDate.minusDays(interval);
        while (true) {
            DayOfWeek dayOfWeek = lastDate.getDayOfWeek();
            //判断是否为周六日
            if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
                //跳出循环进入上一个日期
                lastDate = lastDate.minusDays(1);
                continue;
            }
            //从数据库查找该日期是否在节假日中
            List<CountryHolidays> countryHolidaysList = countryHolidaysRepository.findByDate(startDate);
            if (!CollectionUtils.isEmpty(countryHolidaysList)) {
                //跳出循环进入上一个日期
                lastDate = lastDate.minusDays(1);
                continue;
            }
            return lastDate;
        }
    }

}
