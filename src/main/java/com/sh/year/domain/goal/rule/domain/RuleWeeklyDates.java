package com.sh.year.domain.goal.rule.domain;


import com.sh.year.domain.goal.goal.api.dto.req.RuleWeeklyReqDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleWeeklyDates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weeklyId;
    private int dates; // 일요일 : 1 ~ 월요일 : 7

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private RuleWeeklyDates(int dates) {
        this.dates = dates;
    }

    /**
     * 생성
     */
    public static RuleWeeklyDates createWeeklyDates(RuleWeeklyReqDto ruleWeeklyReqDto){
        return RuleWeeklyDates.builder()
                .dates(ruleWeeklyReqDto.getDates())
                .build();

    }



    public void setRule(Rule rule) {
        this.rule = rule;
    }
}
