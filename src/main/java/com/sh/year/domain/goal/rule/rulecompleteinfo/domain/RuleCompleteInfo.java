package com.sh.year.domain.goal.rule.rulecompleteinfo.domain;

import com.sh.year.domain.goal.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleCompleteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repeatId;
    private int year;
    private int month;
    private byte[] completeDayArr;
    private int totalDayCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    public RuleCompleteInfo(int year, int month, byte[] completeDayArr, int totalDayCnt) {
        this.year = year;
        this.month = month;
        this.completeDayArr = completeDayArr;
        this.totalDayCnt = totalDayCnt;
    }


    /**
     * 생성
     */
    public static RuleCompleteInfo createRepeatDates(int year, int month, byte[] completeDayArr, int totalDayCnt){
        return RuleCompleteInfo.builder()
                .year(year)
                .month(month)
                .completeDayArr(completeDayArr)
                .totalDayCnt(totalDayCnt)
                .build();
    }

    public void updateCompleteDayArr(Integer day){
        this.completeDayArr = completeDayArr;

    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }


}
