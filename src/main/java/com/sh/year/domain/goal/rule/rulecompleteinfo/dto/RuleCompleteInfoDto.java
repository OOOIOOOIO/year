package com.sh.year.domain.goal.rule.rulecompleteinfo.dto;

import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleCompleteInfoDto {
    private int year;
    private int month;
    private byte[] completeDayArr;
    private int totalDayCnt;

    public RuleCompleteInfoDto(RuleCompleteInfo ruleCompleteInfo) {
        this.year = ruleCompleteInfo.getYear();
        this.month = ruleCompleteInfo.getMonth();
        this.completeDayArr = ruleCompleteInfo.getCompleteDayArr();
        this.totalDayCnt = ruleCompleteInfo.getTotalDayCnt();
    }
}
