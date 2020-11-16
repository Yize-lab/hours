package com.tencent.hours.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.tencent.hours.entity.base.BaseEntity;

import javax.persistence.*;

/**
 * @author Gaohj
 * @package com.tencent.hours.entity
 * @Description
 * @date 2020/11/13 15:47
 */
@Table(name = "t_employee_leader")
@Entity
@Access(AccessType.FIELD)
public class EmployeeLeader extends BaseEntity {

    @Column(columnDefinition = " varchar(10) COMMENT '姓名'")
    @ExcelProperty("姓名")
    private String name;

    @Column(columnDefinition = " varchar(20) COMMENT '部门'")
    @ExcelProperty("所属部门")
    private String department;

    @Column(columnDefinition = " varchar(30) COMMENT '邮箱'")
    @ExcelProperty("公司邮箱")
    private String email;

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
