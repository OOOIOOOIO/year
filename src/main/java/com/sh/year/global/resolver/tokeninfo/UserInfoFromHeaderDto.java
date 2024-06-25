package com.sh.year.global.resolver.tokeninfo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInfoFromHeaderDto {
    private Long userId;
    private String email;
    private String provider;

    public UserInfoFromHeaderDto(Long userId, String email, String provider) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
    }
}
