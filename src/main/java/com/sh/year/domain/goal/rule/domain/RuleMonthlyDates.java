package com.sh.year.domain.goal.rule.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleMonthlyDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long monthlyId;
    private Integer days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private RuleMonthlyDates(Integer days) {
        this.days = days;
    }

    /**
     * 생성
     */
    public static RuleMonthlyDates createMonthlyDates(Integer day){
        return RuleMonthlyDates.builder()
                .days(day)
                .build();
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
