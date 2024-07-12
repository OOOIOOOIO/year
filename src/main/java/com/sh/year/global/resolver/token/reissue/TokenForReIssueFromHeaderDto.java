package com.sh.year.global.resolver.token.reissue;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenForReIssueFromHeaderDto {
    private Long userId;
    private String email;
    private String provider;

    public TokenForReIssueFromHeaderDto(Long userId, String email, String provider) {
        this.userId = userId;
        this.email = email;
        this.provider = provider;
    }
}
