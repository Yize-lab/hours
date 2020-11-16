package com.tencent.hours.task;

import com.tencent.hours.dto.DepWarnCountDto;
import com.tencent.hours.dto.EmployeeLackDto;
import com.tencent.hours.entity.EmployeeLeader;
import com.tencent.hours.repository.EmployeeLeaderRepository;
import com.tencent.hours.repository.HoursTempRepository;
import com.tencent.hours.service.WarnService;
import com.tencent.hours.util.MailSenderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ghj
 * @Description 每周统计任务
 * @date 2020/11/5
 */
@Component
@Slf4j
public class WeekStatisticsTask {
    @Autowired
    private WarnService warnService;
    @Autowired
    private HoursTempRepository hoursTempRepository;
    @Autowired
    private MailSenderUtil mailSenderUtil;
    @Autowired
    private EmployeeLeaderRepository employeeLeaderRepository;

    //    @Scheduled(cron = "* 0/5 * ? * *")
    public void sendWeekWarnMail() {
        List<DepWarnCountDto> depWarnCountDtoList = warnService.findWeekWarnGroupByEmp();
        List<EmployeeLackDto> employeeLackDtoList = warnService.findEmpList();
        if (depWarnCountDtoList != null) {
            List<EmployeeLeader> employeeLeaderList = employeeLeaderRepository.findAll();
            List<String> leaderMailList = employeeLeaderList.stream().map(leader -> leader.getEmail()).collect(Collectors.toList());
//            List<String> tos = Arrays.asList("gaohj@tcfuture.tech", "wangl@tcfuture.tech");
            try {
                mailSenderUtil.sendLeader(leaderMailList, depWarnCountDtoList, employeeLackDtoList);
                log.info("工时填报统计邮件发送任务执行成功");
            } catch (Exception e) {
                log.info("工时填报统计邮件发送任务执行出现异常：{}", e);
                throw new RuntimeException(e);
            }
        }
    }

    @Scheduled(cron = "0 0 0 ? * *")
    public void delHoursTemp() {
        hoursTempRepository.deleteAll();
        log.info(" delete all hours temp data!");
    }
}
