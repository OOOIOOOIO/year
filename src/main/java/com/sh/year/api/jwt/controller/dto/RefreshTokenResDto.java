package com.sh.year.api.jwt.controller.dto;

/**
 * refreshToken 만료시
 *
 * - accessToken 재발급
 * - refreshToken 재발급
 */
public class RefreshTokenResDto {

    private String accessToken;
    private String refreshToken;

    public RefreshTokenResDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}


