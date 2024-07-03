package com.sh.year.domain.user.api.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInfoUpdateReqDto {

    private String nickname;
    private String profileImg;
    private String instagramAccount;
    private String stateMessage;

}
