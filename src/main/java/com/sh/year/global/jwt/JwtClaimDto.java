package com.sh.year.global.jwt;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtClaimDto {

    private Long userId;
    private String email;
    private String provider;
    private String tokenType;


    public JwtClaimDto(Long userId, String email, String provider, String tokenType) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
        this.tokenType = tokenType;
    }

}
