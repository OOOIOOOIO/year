package com.sh.year.api.main.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DelayGoalResDto {

    private Long delayGoalId;
    private Long smallGoalId;
    private String title;
    private String icon;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int failStatus;
    private RuleResDto ruleResDto;
    private float progress;

    public DelayGoalResDto(SmallGoal smallGoal, Rule rule, int failStatus, Long delayGoalId) {
        this.delayGoalId = delayGoalId;
        this.smallGoalId = smallGoal.getSmallGoalId();
        this.title = smallGoal.getTitle();
        this.icon = smallGoal.getIcon();
        this.endDate = smallGoal.getEndDate();
        this.failStatus = failStatus;
        this.ruleResDto = new RuleResDto(rule);
        this.progress = 0;

    }

    public void setProgress(float progress) {
        this.progress = progress;
    }
}
