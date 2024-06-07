package com.sh.year.domain.goal.goal.failgoal.domain;


import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FailGoal extends BaseTimeEntity {

    /**
     * 작은목표fk -> 내용만 가져오고
     * 유예기간
     * 원래 달성일layE     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long failGoalId;
    private LocalDate delayEndDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private FailGoal(LocalDate delayEndDate, Rule rule) {
        this.delayEndDate = delayEndDate;
        this.rule = rule;
    }

//    public static FailGoal createFailGoal(FailGoalReqDto failGoalReqDto, Rule rule){
//
//
//    }


    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
