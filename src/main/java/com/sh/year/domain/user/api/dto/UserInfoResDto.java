package com.sh.year.domain.user.api.dto;

import com.sh.year.domain.user.domain.Users;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.core.io.UrlResource;

@Getter
@NoArgsConstructor
public class UserInfoResDto {

    private String email;
    private String nickname;
    private String provider;
    private String instagramAccount;
    private String stateMessage;
    private UrlResource profileImg;

    public UserInfoResDto(Users users, UrlResource profileImg) {
        this.email = users.getEmail();
        this.nickname = users.getNickname();
        this.provider = users.getProvider();
        this.instagramAccount = users.getInstagramAccount();
        this.stateMessage = users.getStateMessage();
        this.profileImg = profileImg;
    }

    public void setProfileImg(UrlResource profileImg){
        this.profileImg = profileImg;
    }
}
