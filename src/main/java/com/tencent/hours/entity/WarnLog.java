package com.tencent.hours.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.tencent.hours.entity.base.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Table(name = "t_warn_log")
@Entity
@Access(AccessType.FIELD)
public class WarnLog extends BaseEntity {
    @Column(columnDefinition = " varchar(20) COMMENT 'p账号'")
    @ExcelProperty("p账号")
    private String pAccount;

    @Column(columnDefinition = " bit(1) COMMENT '邮件发送状态 1：成功'")
    @ExcelIgnore
    private Boolean sendStatus;

    @Column(columnDefinition = " varchar(300) COMMENT '错误信息'")
    @ExcelIgnore
    private String error;

    @Column(columnDefinition = " date COMMENT '缺少工时的日期'")
    @ExcelProperty("缺少工时的日期")
    private LocalDate lackDate;

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public Boolean getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Boolean sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDate getLackDate() {
        return lackDate;
    }

    public void setLackDate(LocalDate lackDate) {
        this.lackDate = lackDate;
    }
}
