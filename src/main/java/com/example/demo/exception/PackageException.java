package com.example.demo.exception;

import lombok.Getter;

@Getter
public class PackageException extends RuntimeException{

    private final ErrorCode errorCode;
    private final String message;

    public PackageException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return "[%s] %s".formatted(errorCode, message);
    }
}
