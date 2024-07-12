package com.sh.year.domain.goal.goal.delayGoal.domain;


import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
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
    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus; // 0 : delay / 1 : comp / -1 : fail

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private DelayGoal(LocalDate endDate, CompleteStatus completeStatus, Rule rule, Users users) {
        this.endDate = endDate;
        this.completeStatus = completeStatus;
        this.rule = rule;
        this.users = users;
    }

    public static DelayGoal createDelayGoal(Rule rule, Users users){
        return DelayGoal.builder()
                .endDate(LocalDate.now().plusDays(6)) // 시작일 포함 7일
                .completeStatus(CompleteStatus.DELAY) //default : 0
                .rule(rule)
                .users(users)
                .build();

    }

    public void updateCompleteStatusToComplete(){
        this.completeStatus = CompleteStatus.COMP;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void updateFailStatus(int completeStatus){
        if(completeStatus == -1){
            this.completeStatus = CompleteStatus.FAIL;
        }
        else if(completeStatus == 0){
            this.completeStatus = CompleteStatus.DELAY;
        }
        else{
            this.completeStatus = CompleteStatus.COMP;
        }
    }



}
