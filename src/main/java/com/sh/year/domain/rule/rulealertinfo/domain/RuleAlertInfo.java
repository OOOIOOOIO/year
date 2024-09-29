package com.sh.year.domain.rule.rulealertinfo.domain;

import com.sh.year.domain.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleAlertInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long alertId;
    private int year;
    private int month;

    private byte[] alertDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    public RuleAlertInfo(int year, int month, byte[] alertDay) {
        this.year = year;
        this.month = month;
        this.alertDay = alertDay;
    }


    /**
     * 생성
     */
    public static RuleAlertInfo createRuleAlertInfo(int year, int month, byte[] alertDay){
        return RuleAlertInfo.builder()
                .year(year)
                .month(month)
                .alertDay(alertDay)
                .build();
    }

    public void updateAlertDay(byte[] alertDay){
        this.alertDay = alertDay;

    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }


}
