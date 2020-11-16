package com.tencent.hours.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Data
public class EmpWarnDto {

    private String pAccount;

    private String name;

    private String department;
    /**
     * 最近一次花费日期
     */
    private LocalDate lastSpendDate;

    private String email;
}
