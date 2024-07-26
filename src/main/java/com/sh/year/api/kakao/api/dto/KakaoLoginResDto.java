package com.sh.year.api.kakao.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
