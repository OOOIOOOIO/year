package com.sh.year.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@Getter
@AllArgsConstructor
public enum JwtCustomErrorCode {


    // jwt
    RefreshTokenExpiredException(FORBIDDEN, "J001", "Refresh Token이 만료되었습니다."),
    SignatureException(FORBIDDEN, "J004", "JWT의 Signature가 일치하지 않습니다."),
    MalformedJwtException(FORBIDDEN, "J003", "JWT 구조가 잘못되었습니다."),
    UnsupportedJwtException(FORBIDDEN, "J002", "JWT의 형식이 잘못되었습니다."),
    AccessTokenExpiredException(FORBIDDEN, "J005", "Access Token이 만료되었습니다."),
    AccessTokenNotMatchWithRedisException(FORBIDDEN, "J006", "Header와 Rdeis의 Token 정보가 일치하지 않습니다."),
    RefreshTokenNotMatchWithRedisException(FORBIDDEN, "J007", "Header와 Rdeis의 Token 정보가 일치하지 않습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
