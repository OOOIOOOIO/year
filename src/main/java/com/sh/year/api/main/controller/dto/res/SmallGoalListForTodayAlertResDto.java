package com.sh.year.api.main.controller.dto.res;

import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
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
    private Integer progress;

    public SmallGoalListForTodayAlertResDto(TodayAlertSmallGoalInterface todayAlertSmallGoalInterface, Rule rule) {
        this.smallGoalId = todayAlertSmallGoalInterface.getSmallGoalId();
        this.title = todayAlertSmallGoalInterface.getTitle();
        this.icon = todayAlertSmallGoalInterface.getIcon();
        this.ruleResDto = new RuleResDto(rule);
        this.progress = 0;

    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

}


