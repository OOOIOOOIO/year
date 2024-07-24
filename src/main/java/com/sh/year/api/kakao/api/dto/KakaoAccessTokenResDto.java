package com.sh.year.api.kakao.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccessTokenResDto {
    private String token_type;
    private String access_token;
    private String refresh_token;
}
