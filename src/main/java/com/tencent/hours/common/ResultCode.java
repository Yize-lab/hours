

package com.tencent.hours.common;

public enum ResultCode implements IResultCode {

    SUCCESS(200, "操作成功"),
    INTERNAL_SERVER_ERROR(500, "服务器异常"),


    ;

    final int code;
    final String message;

    private ResultCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
