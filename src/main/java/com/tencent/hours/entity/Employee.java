package com.tencent.hours.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.tencent.hours.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * @author ghj
 * @Description 员工基础信息
 * @date 2020/11/4
 */
@Table(name = "t_employee", indexes = {@Index(name = "idx_p", columnList = "pAccount")})
@Entity
@Access(AccessType.FIELD)
public class Employee extends BaseEntity {

    @Column(columnDefinition = " varchar(20) COMMENT 'p账号'")
    @ExcelProperty("腾讯云账号")
    private String pAccount;

    @Column(columnDefinition = " varchar(10) COMMENT '姓名'")
    @ExcelProperty("姓名")
    private String name;

    @Column(columnDefinition = " varchar(20) COMMENT '部门'")
    @ExcelProperty("所属部门")
    private String department;

    @Column(columnDefinition = " varchar(30) COMMENT '邮箱'")
    @ExcelProperty("公司邮箱")
    private String email;

    public String getpAccount() {
        return pAccount;
    }

    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
