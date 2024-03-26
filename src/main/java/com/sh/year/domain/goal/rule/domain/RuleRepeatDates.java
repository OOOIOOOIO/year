package com.sh.year.domain.goal.rule.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleRepeatDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repeatId;
    private Integer days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private RuleRepeatDates(Integer days) {
        this.days = days;
    }

    /**
     * 생성
     */
    public static RuleRepeatDates createRepeatDates(Integer day){
        return RuleRepeatDates.builder()
                .days(day)
                .build();
    }

    public void updateRepeatDates(Integer day){
        this.days = day;

    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
