package com.sh.year.global.resolver.userInfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFromTokenDto {
    private Long userId;
    private String email;
    private String provider;

    public UserInfoFromTokenDto(Long userId, String email, String provider) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
    }
}
