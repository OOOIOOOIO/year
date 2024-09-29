package com.sh.year.domain.user.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.rule.delayrule.domain.DelayRule;
import com.sh.year.domain.user.api.dto.UserInfoUpdateReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String provider;
    private String nickname;
    private String profileImg;
    private String instagramAccount;
    private String stateMessage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<BigGoal> bigGoalList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<DelayRule> delayRuleList = new ArrayList<>();


    @Builder
    public Users(String email, String provider, Role role) {
        this.email = email;
        this.provider = provider;
        this.role = role;
    }

    public void updateRole(Role roles) {
        this.role = roles;
    }
    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addBigGoal(BigGoal bigGoal){
        if(bigGoal.getUsers() != null){
            bigGoal.getUsers().getBigGoalList().remove(bigGoal);
        }
        this.bigGoalList.add(bigGoal);
        bigGoal.setUsers(this);
    }

    public void addDelayGoal(DelayRule delayRule){
        if(delayRule.getUsers() != null){
            delayRule.getUsers().getBigGoalList().remove(delayRule);
        }
        this.delayRuleList.add(delayRule);
        delayRule.setUsers(this);
    }

    /**
     * user 생성
     */
    public static Users createUser(String email, String provider, Role role){
        return Users.builder()
                .email(email)
                .provider(provider)
                .role(role)
                .build();
    }

    public Users update(String email, String picture, String provider){
        this.email = email;
        this.provider = provider;

        return this;
    }

    public void updateUserInfo(UserInfoUpdateReqDto userInfoUpdateReqDto, String newFileName) {
        this.nickname = userInfoUpdateReqDto.getNickname();
        this.profileImg = newFileName;
        this.instagramAccount = userInfoUpdateReqDto.getInstagramAccount();
        this.stateMessage = userInfoUpdateReqDto.getStateMessage();
    }

}
