package com.tencent.hours.controller;

import com.tencent.hours.task.WeekStatisticsTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ghj
 * @Description
 * @date 2020/11/6
 */
@Api(tags = "测试接口")
@RestController("api/test")
public class TestController {
    @Autowired
    private WeekStatisticsTask weekStatisticsTask;

    @GetMapping("weekWarn")
    @ApiOperation("部门leader发送统计邮件")
    public void send(){
        weekStatisticsTask.sendWeekWarnMail();
    }


    @GetMapping("test")
    @ApiOperation("测试")
    public void test(){
        System.err.println(Thread.currentThread().getName());
        weekStatisticsTask.test();
    }
}
