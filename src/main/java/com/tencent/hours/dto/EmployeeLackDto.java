package com.tencent.hours.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Gaohj
 * @package com.tencent.hours.dto
 * @Description
 * @date 2020/11/12 16:59
 */
public class EmployeeLackDto {

    private Integer number;

    private String name;

    private String pAccount;

    private String department;

    private LocalDate lackDate;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public LocalDate getLackDate() {
        return lackDate;
    }

    public void setLackDate(LocalDate lackDate) {
        this.lackDate = lackDate;
    }
}
