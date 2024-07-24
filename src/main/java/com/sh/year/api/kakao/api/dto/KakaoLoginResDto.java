package com.sh.year.api.kakao.api.dto;

public class KakaoLoginResDto {
    private String accessToken;
    private String refreshToken;
    private boolean isExist;

    public KakaoLoginResDto(String accessToken, String refreshToken, boolean isExist) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.isExist = isExist;
    }
}
