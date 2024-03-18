package com.sh.year.api.jwt.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenResDto {

    private String AccessToken;


    public AccessTokenResDto(String accessToken) {
        AccessToken = accessToken;
    }
}
