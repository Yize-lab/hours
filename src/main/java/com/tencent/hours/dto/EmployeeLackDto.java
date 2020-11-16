package com.tencent.hours.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Gaohj
 * @package com.tencent.hours.dto
 * @Description
 * @date 2020/11/12 16:59
 */
@Data
public class EmployeeLackDto {

    private Integer number;

    private String name;

    private String department;

    private LocalDate lackDate;
}
