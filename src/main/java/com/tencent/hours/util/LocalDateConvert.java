package com.tencent.hours.util;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.tencent.hours.common.CommonConstant;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
public class LocalDateConvert implements Converter<LocalDate> {


    @Override
    public Class supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDate convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        String data = null;
        if (CellDataTypeEnum.NUMBER.equals(cellData.getType())) {
            String[] split = cellData.toString().split("\\.");
            LocalDate of = LocalDate.of(1899, 12, 30);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstant.DateFormat.DATE_FORMAT);
            data = formatter.format(of.plusDays(Long.parseLong(split[0])));
        } else {
            data = cellData.getStringValue();
        }
        return LocalDate.parse(data, DateTimeFormatter.ofPattern(CommonConstant.DateFormat.DATE_FORMAT));
    }

    @Override
    public CellData convertToExcelData(LocalDate value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return new CellData<>(value.format(DateTimeFormatter.ofPattern(CommonConstant.DateFormat.DATE_FORMAT)));
    }
}
