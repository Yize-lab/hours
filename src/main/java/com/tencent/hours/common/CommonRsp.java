package com.tencent.hours.common;

import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author ghj
 * @Description
 * @date 2020/11/4
 */
@Data
@ToString
public class CommonRsp<T> implements Serializable {

    private static final long serialVersionUID = -2980987226459285144L;

    private int code;

    private boolean success;

    private T data;

    private String msg;

    private CommonRsp() {
    }

    private CommonRsp(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private CommonRsp(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private CommonRsp(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private CommonRsp(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private CommonRsp(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static boolean isSuccess(@Nullable CommonRsp<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ResultCode.SUCCESS.code == x.code;
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable CommonRsp<?> result) {
        return !isSuccess(result);
    }

    public static <T> CommonRsp<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> CommonRsp<T> data(T data, String msg) {
        return data(200, data, msg);
    }

    public static <T> CommonRsp<T> data(int code, T data, String msg) {
        return new CommonRsp(code, data, msg);
    }

    public static <T> CommonRsp<T> success(String msg) {
        return new CommonRsp(ResultCode.SUCCESS, msg);
    }

    public static <T> CommonRsp<T> success(IResultCode resultCode) {
        return new CommonRsp(resultCode);
    }

    public static <T> CommonRsp<T> success(IResultCode resultCode, String msg) {
        return new CommonRsp(resultCode, msg);
    }

    public static <T> CommonRsp<T> fail(String msg) {
        return new CommonRsp(ResultCode.INTERNAL_SERVER_ERROR, msg);
    }

    public static <T> CommonRsp<T> fail(int code, String msg) {
        return new CommonRsp(code, (Object) null, msg);
    }

    public static <T> CommonRsp<T> fail(IResultCode resultCode) {
        return new CommonRsp(resultCode);
    }

    public static <T> CommonRsp<T> fail(IResultCode resultCode, String msg) {
        return new CommonRsp(resultCode, msg);
    }

    public static <T> CommonRsp<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }
}
