package com.sh.year.domain.user.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String picture;
    private String provider;
    private String nickname;
    private String instagramAccount;
    private String stateMessage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Goal> goalList = new ArrayList<>();


    @Builder
    public Users(String email, String picture, String provider, Role role) {
        this.email = email;
        this.picture = picture;
        this.provider = provider;
        this.role = role;
    }

    public void updateRole(Role roles) {
        this.role = roles;
    }
    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addGoal(Goal goal){
        if(goal.getUsers() != null){
            goal.getUsers().getGoalList().remove(goal);
        }
        this.goalList.add(goal);
        goal.setUsers(this);
    }

    /**
     * user 생성
     */
    public static Users createUser(String email, String picture, String provider, Role role){
        return Users.builder()
                .email(email)
                .picture(picture)
                .provider(provider)
                .role(role)
                .build();
    }

    public Users update(String email, String picture, String provider){
        this.email = email;
        this.picture = picture;
        this.provider = provider;

        return this;
    }

}
