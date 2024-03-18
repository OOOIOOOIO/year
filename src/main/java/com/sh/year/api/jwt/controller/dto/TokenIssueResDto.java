package com.sh.year.api.jwt.controller.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenIssueResDto {
    private String authorization;
    private String refreshToken;

    public TokenIssueResDto(String authorization, String refreshToken) {
        this.authorization = authorization;
        this.refreshToken = refreshToken;
    }
}
