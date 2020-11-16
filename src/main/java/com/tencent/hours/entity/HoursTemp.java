package com.tencent.hours.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.tencent.hours.entity.base.BaseEntity;
import com.tencent.hours.util.LocalDateConvert;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Table(name = "t_hours_temp")
@Entity
@Access(AccessType.FIELD)
public class HoursTemp extends BaseEntity {
    @Column(columnDefinition = " varchar(20) COMMENT 'p账号'")
    @ExcelProperty("花费创建人")
    private String pAccount;

    @Column(columnDefinition = " date COMMENT '花费日期'")
    @ExcelProperty(value = "花费日期",converter = LocalDateConvert.class)
    private LocalDate spendDate;

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public LocalDate getSpendDate() {
        return spendDate;
    }

    public void setSpendDate(LocalDate spendDate) {
        this.spendDate = spendDate;
    }
}
