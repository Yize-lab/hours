package com.tencent.hours.service;

import com.tencent.hours.common.UserType;
import com.tencent.hours.dto.DepWarnCountDto;
import com.tencent.hours.dto.EmployeeLackDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
public interface WarnService {
    /**
     * 解析文件保存到数据库
     * @param file
     * @param userType
     */
    void uploadRoster(MultipartFile file, UserType userType);

    /**
     * 根据工时表格通过邮件告警
     * @param file
     */
    void warningPrompt(MultipartFile file);

    /**
     * 设置提醒的阈值
     * @param threshold
     */
    void setThreshold(Integer threshold);

    /**
     * 根据部门统计每周的告警数据
     * @return
     */
    List<DepWarnCountDto> findWeekWarnGroupByEmp();

    void uploadCountryHoliday(MultipartFile file);

    /**
     * 获取缺失工时员工列表
     * @return
     */
    List<EmployeeLackDto> findEmpList();

    Integer getThreshold();
}
