package com.tencent.hours.config;

import com.tencent.hours.common.CommonRsp;
import com.tencent.hours.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ghj
 * @Description
 * @date 2020/11/9
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {


    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonRsp constraintException(Exception ex) {
        log.error("unknow exception", ex);
        return CommonRsp.fail(ResultCode.INTERNAL_SERVER_ERROR + ex.getCause().toString().substring(0, 100));
    }

    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.OK)
    public CommonRsp noHandlerFoundException(Exception e) {
        log.error("no handler found exception", e);
        return CommonRsp.fail(e.getMessage());
    }


}