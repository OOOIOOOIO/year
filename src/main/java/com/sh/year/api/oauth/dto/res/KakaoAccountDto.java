package com.sh.year.api.oauth.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccountDto {

    private KakaoProfileDto profile;
    private String email;
}
