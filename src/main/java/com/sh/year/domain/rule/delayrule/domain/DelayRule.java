package com.sh.year.domain.rule.delayrule.domain;


import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.rule.rule.domain.Rule;
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
public class DelayRule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long delayRuleId;
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
    private DelayRule(LocalDate endDate, CompleteStatus completeStatus, Rule rule, Users users) {
        this.endDate = endDate;
        this.completeStatus = completeStatus;
        this.rule = rule;
        this.users = users;
    }

    public static DelayRule createDelayGoal(Rule rule, Users users){
        return DelayRule.builder()
                .endDate(LocalDate.now().plusDays(2)) // 시작일 포함 3일
                .completeStatus(CompleteStatus.DELAY) //default : 0
                .rule(rule)
                .users(users)
                .build();

    }

    public void updateCompleteStatusToComplete(){
        this.completeStatus = CompleteStatus.COMP;
    }

    public void updateCompleteStatusToFail(){
        this.completeStatus = CompleteStatus.FAIL;
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
