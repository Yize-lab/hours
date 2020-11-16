package com.tencent.hours.controller;

import com.tencent.hours.common.UserType;
import com.tencent.hours.service.WarnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@RestController
@Api(tags = "告警通知")
@RequestMapping("api/hours")
public class WarnController {
    @Autowired
    private WarnService warnService;

    @ApiOperation("花名册录入&部门leader资料导入")
    @PostMapping("upload/roster")
    public void uploadFile(@RequestBody @ApiParam("花名册名单") MultipartFile file, UserType userType) {
        warnService.uploadRoster(file, userType);
    }

    @ApiOperation("检测并发送告警邮件")
    @PostMapping("warning")
    public void warningPrompt(@RequestBody @ApiParam("工时表格") MultipartFile file) {
        warnService.warningPrompt(file);
    }

    @ApiOperation("设置提醒阈值")
    @PutMapping("threshold")
    public void setThreshold(Integer threshold) {
        warnService.setThreshold(threshold);
    }

    @ApiOperation("国家法定节假日数据导入")
    @PostMapping("upload/holiday")
    public void uploadCountryHoliday(@RequestBody MultipartFile file) {
        warnService.uploadCountryHoliday(file);
    }


}
