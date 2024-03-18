package com.sh.year.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handle CustomException : {}", e.getCustomErrorCode());

        return ErrorResponse.toResponseEntity(e.getCustomErrorCode());
    }
}
