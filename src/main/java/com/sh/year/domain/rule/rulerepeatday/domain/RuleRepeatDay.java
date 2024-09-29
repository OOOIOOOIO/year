package com.sh.year.domain.rule.rulerepeatday.domain;

import com.sh.year.domain.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleRepeatDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleRepeatDayId;

    private int day; // 마지막날이면 -1 / 월요일 1, 일요일 7

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private RuleRepeatDay(int day, Rule rule) {
        this.day = day;
    }

    public static RuleRepeatDay createRuleRepeatDay(int day){
        return RuleRepeatDay.builder()
                .day(day)
                .build();
    }


    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
