package com.minio.enums;

import com.minio.constant.MessagesConstant;

/**
 * 数据响应状态码
 */
public enum CodeEnum implements BaseErrorInfo {
    SUCCESS(200, MessagesConstant.SUCCESS),
    BODY_NOT_MATCH(400, MessagesConstant.PARAMS_ERROR),
    NOT_AUTHENTICATION(401, MessagesConstant.NOT_AUTHENTICATION),
    LOGIN_EXITED(402, MessagesConstant.LOGIN_EXITED),
    FORBIDDEN(403, MessagesConstant.FORBIDDEN),
    NOT_FOUND(404, MessagesConstant.NOT_FOUND),
    USERNAME_PASSWORD_ERROR(405, MessagesConstant.USERNAME_PASSWORD_ERROR),
    ACCOUNT_EXPIRED(406, MessagesConstant.ACCOUNT_EXPIRED),
    ACCOUNT_DISABLE(407, MessagesConstant.ACCOUNT_DISABLE),
    ACCOUNT_LOCKED(408, MessagesConstant.ACCOUNT_LOCKED),
    ACCOUNT_NOT_EXIST(410, MessagesConstant.ACCOUNT_NOT_EXIST),
    INTERNAL_SERVER_ERROR(500, MessagesConstant.INTERNAL_SERVER_ERROR),
    SERVER_BUSY(503,MessagesConstant.SERVER_BUSY);

    /** 错误码 */
    private Integer code;

    /** 错误描述 */
    private String message;

    CodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
