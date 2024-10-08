package com.sh.year.api.main.controller.dto.res;

import com.sh.year.domain.smallgoal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.rule.rule.domain.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalListForTodayAlertResDto {

    private Long smallGoalId;
    private String title;
    private String icon;
    private RuleResDto ruleResDto;
    private String progress;

    public SmallGoalListForTodayAlertResDto(TodayAlertSmallGoalInterface todayAlertSmallGoalInterface, Rule rule) {
        this.smallGoalId = todayAlertSmallGoalInterface.getSmallGoalId();
        this.title = todayAlertSmallGoalInterface.getTitle();
        this.icon = todayAlertSmallGoalInterface.getIcon();
        this.ruleResDto = new RuleResDto(rule);

    }

    public void setProgress(float progress) {
        this.progress = String.format("%.5f", progress);
    }

}


