package com.sh.year.domain.user.domain;

import com.sh.year.domain.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String picture;
    private String password;
    private String nickname;
    private String instagramAccount;
    private String stateMessage;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "USER_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "users", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<Goal> goalList = new ArrayList<>();

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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



}
