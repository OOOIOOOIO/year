package com.sh.year.domain.goal.rule.rulerepeatday.dto;

import com.sh.year.domain.goal.rule.rulerepeatday.domain.RuleRepeatDay;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleRepeatDayDto {
    private int day;

    public RuleRepeatDayDto(RuleRepeatDay ruleRepeatDay) {
        this.day = ruleRepeatDay.getDay();
    }
}
