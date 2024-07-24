package com.sh.year.api.kakao.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoUserInfoResDto {

    private String email;
    private String provider;

    public KakaoUserInfoResDto(String email, String provider) {
        this.email = email;
        this.provider = provider;
    }
}
