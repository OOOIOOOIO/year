package com.sh.year.global.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
@Getter
//@RequiredArgsConstructor
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime timestamp;

    private  int status;
    private  String error;
    private  String code;
    private  String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(CustomErrorCode customErrorCode) {

        return ResponseEntity
                .status(customErrorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .timestamp(LocalDateTime.now())
                        .status(customErrorCode.getHttpStatus().value())
                        .error(customErrorCode.name())
                        .code(customErrorCode.getCode())
                        .message(customErrorCode.getMessage())
                        .build()
                );
    }

    public static ErrorResponse createJwtErrorResponse(JwtCustomErrorCode customErrorCode) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(customErrorCode.getHttpStatus().value())
                .error(customErrorCode.name())
                .code(customErrorCode.getCode())
                .message(customErrorCode.getMessage())
                .build();
    }

    public static ErrorResponse createCustomErrorResponse(CustomErrorCode customErrorCode) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(customErrorCode.getHttpStatus().value())
                .error(customErrorCode.name())
                .code(customErrorCode.getCode())
                .message(customErrorCode.getMessage())
                .build();
    }
}
