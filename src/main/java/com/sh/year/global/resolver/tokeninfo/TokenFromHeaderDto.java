package com.sh.year.global.resolver.tokeninfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenFromHeaderDto {
    private String accessToken;
    private String refreshToken;

    public TokenFromHeaderDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
