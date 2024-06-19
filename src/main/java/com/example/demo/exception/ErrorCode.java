package com.example.demo.exception;

public enum ErrorCode {

    PACKAGE_NOT_EXIST("존재하지 않는 패키지입니다.");

    ErrorCode(String message) {
        this.message = message;
    }

    public final String message;
}
