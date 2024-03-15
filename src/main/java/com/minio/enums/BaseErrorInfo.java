package com.minio.enums;

public interface BaseErrorInfo {

    /** 错误码*/
    Integer getCode();

    /** 错误描述*/
    String getMessage();

}
