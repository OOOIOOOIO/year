package com.sh.year.api.oauth.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoAccountInfoResDto {
    private Long id; // 회원번호
    private KakaoAccountDto kakao_account;

}
