package com.sh.year.domain.goal.goal.delayGoal.domain;


import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DelayGoal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delayGoalId;
    private LocalDate endDate;
    private int failStatus; // 0 : delay / 1 : fail

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private DelayGoal(LocalDate endDate, int failStatus, Rule rule, Users users) {
        this.endDate = endDate;
        this.failStatus = failStatus;
        this.rule = rule;
        this.users = users;
    }

    public static DelayGoal createDelayGoal(Rule rule, Users users){
        return DelayGoal.builder()
                .endDate(LocalDate.now().plusDays(7)) // 7일 후까지
                .failStatus(0) //default : 0
                .rule(rule)
                .users(users)
                .build();

    }


    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void updateFailStatus(int failStatus){
        this.failStatus = failStatus == 0 ? 1 : 0;
    }



}
