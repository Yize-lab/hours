package com.tencent.hours.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tencent.hours.entity.CountryHolidays;

/**
 * @author ghj
 * @Description
 * @date 2020/11/5
 */
public class LocalReadListener extends AnalysisEventListener<CountryHolidays> {


    @Override
    public void invoke(CountryHolidays data, AnalysisContext context) {
        System.out.println(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("数据解析完成");
    }
}
