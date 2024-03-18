package com.sh.year.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtCustomException extends RuntimeException {

    private JwtCustomErrorCode jwtCustomErrorCode;
}
