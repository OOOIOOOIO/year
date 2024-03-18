package com.sh.year.global.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomErrorCode customErrorCode) {
        return ResponseEntity
                .status(customErrorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(customErrorCode.getHttpStatus().value())
                        .error(customErrorCode.getHttpStatus().name())
                        .code(customErrorCode.name())
                        .message(customErrorCode.getMessage())
                        .build()
                );
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(JwtCustomErrorCode customErrorCode) {
        return ResponseEntity
                .status(customErrorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .status(customErrorCode.getHttpStatus().value())
                        .error(customErrorCode.getHttpStatus().name())
                        .code(customErrorCode.name())
                        .message(customErrorCode.getMessage())
                        .build()
                );
    }
}
