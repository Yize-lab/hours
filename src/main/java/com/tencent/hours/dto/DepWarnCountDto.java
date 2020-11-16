package com.tencent.hours.dto;

import java.text.DecimalFormat;

/**
 * @author ghj
 * @Description
 * @date 2020/11/5
 */

public class DepWarnCountDto {

    private Integer number;

    private Integer empCount;

    private Integer warnCount;
    /**
     * 时间周期 2020/11/1-2020/11/5
     */
    private String period;

    private String department;
    /**
     * 告警比例
     */
    private String proportion;

    public void setProportion(String proportion) {
        this.proportion = getProportion();
    }

    public String getProportion() {
        String departmentStr = "";
        if (this.empCount != null && this.empCount != 0 && this.warnCount != null) {
            DecimalFormat decimalFormat = new DecimalFormat("0.0");
            departmentStr = decimalFormat.format(((float) this.warnCount / this.empCount) * 100) + "%";
        }
        return departmentStr;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getEmpCount() {
        return empCount;
    }

    public void setEmpCount(Integer empCount) {
        this.empCount = empCount;
    }

    public Integer getWarnCount() {
        return warnCount;
    }

    public void setWarnCount(Integer warnCount) {
        this.warnCount = warnCount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
