package com.sh.year.api.oauth.dto.req;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAuthCodeReqDto {
    private String client_id;
    private String redirect_uri;
    private String response_type; // "code"로 고정

    @Builder
    public KakaoAuthCodeReqDto(String client_id, String redirect_uri, String response_type) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.response_type = response_type;
    }
}
