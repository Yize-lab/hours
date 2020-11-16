package com.tencent.hours.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDate;


/**
 * @author ghj
 * @Description 员工excel映射
 * @date 2020/11/4
 */
@Data
public class EmpHoursExcelDto {
    @ExcelProperty("p账号")
    private String pAccount;

//    @ExcelProperty("姓名")
//    private String name;
//
//    @ExcelProperty("部门")
//    private String department;

    @ExcelProperty("花费日期")
    private LocalDate spendDate;



}
