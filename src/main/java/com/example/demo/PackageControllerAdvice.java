package com.example.demo;

import com.example.demo.dto.PackagesExceptionResponseDto;
import com.example.demo.exception.PackageException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class PackageControllerAdvice {

    @ExceptionHandler(PackageException.class)
    public PackagesExceptionResponseDto couponIssueExceptionHandler(PackageException exception) {
        return new PackagesExceptionResponseDto(false, exception.getErrorCode().message);
    }
}
