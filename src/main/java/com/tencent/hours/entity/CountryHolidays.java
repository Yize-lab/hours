package com.tencent.hours.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.tencent.hours.entity.base.BaseEntity;
import com.tencent.hours.util.LocalDateConvert;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author ghj
 * @Description
 * @date 2020/11/5
 */
@Table(name = "t_country_holidays")
@Entity
@Access(AccessType.FIELD)
@Data
public class CountryHolidays extends BaseEntity {
    @Column(columnDefinition = " int(6) not null default '-1' COMMENT '年份'")
    @ExcelProperty("年份")
    private Integer year;
    @Column(columnDefinition = " date COMMENT '日期'")
    @ExcelProperty(value = "日期", converter = LocalDateConvert.class)
    private LocalDate date;


}
