package com.sh.year.domain.goal.rule.domain;

import com.sh.year.domain.goal.goal.api.dto.req.RuleMonthlyReqDto;
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
    private LocalDate days;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private RuleMonthlyDates(LocalDate days) {
        this.days = days;
    }

    /**
     * 생성
     */
    public static RuleMonthlyDates createMonthlyDates(RuleMonthlyReqDto ruleMonthlyReqDto){
        return RuleMonthlyDates.builder()
                .days(ruleMonthlyReqDto.getDays())
                .build();
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
