package com.sh.year.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class JwtExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { JwtCustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(JwtCustomException e) {

        /**
         * Access Token 만료
         *
         * Refresh Token 만료
         *
         */
        log.error("handel JwtException : {}", e.getJwtCustomErrorCode());

        return ErrorResponse.toResponseEntity(e.getJwtCustomErrorCode());
    }
}
