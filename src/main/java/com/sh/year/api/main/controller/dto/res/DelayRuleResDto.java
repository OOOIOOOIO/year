package com.sh.year.api.main.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.smallgoal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.rule.rule.domain.Rule;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DelayRuleResDto {

    private Long delayGoalId;
    private Long smallGoalId;
    private String title;
    private String icon;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int completeStatus;
    private RuleResDto ruleResDto;
    private String progress;

    public DelayRuleResDto(SmallGoal smallGoal, Rule rule, CompleteStatus completeStatus, Long delayGoalId, LocalDate endDate) {
        this.delayGoalId = delayGoalId;
        this.smallGoalId = smallGoal.getSmallGoalId();
        this.title = smallGoal.getTitle();
        this.icon = smallGoal.getIcon();
        this.endDate = endDate;
        this.ruleResDto = new RuleResDto(rule);

        if(completeStatus.equals(CompleteStatus.FAIL)){
            this.completeStatus = -1;
        }
        else if(completeStatus.equals(CompleteStatus.DELAY)){
            this.completeStatus = 0;
        }
        else{
            this.completeStatus = 1;
        }

    }

    public void setProgress(float progress) {
        this.progress = String.format("%.5f", progress);
    }
}
