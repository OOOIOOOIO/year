package com.sh.year.domain.smallgoal.smallgoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.api.main.controller.dto.res.DelayRuleResDto;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.smallgoal.domain.SmallGoal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalResDto {

    private Long smallGoalId;
    private String title;
    private String icon;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int completeStatus;
    private RuleResDto ruleResDto;
    private String progress;

    private List<DelayRuleResDto> delayGoalList;

    public SmallGoalResDto(SmallGoal smallGoal) {
        this.smallGoalId = smallGoal.getSmallGoalId();
        this.title = smallGoal.getTitle();
        this.icon = smallGoal.getIcon();
        this.endDate = smallGoal.getEndDate();
        this.completeStatus = smallGoal.getCompleteStatus().equals(CompleteStatus.COMP) ? 1 : 0;
        this.ruleResDto = new RuleResDto(smallGoal.getRule());
    }

    public void setProgress(float progress) {

        this.progress = String.format("%.5f", progress);
    }

    public void setDelayGoalList(List<DelayRuleResDto> delayGoalList){
        this.delayGoalList = delayGoalList;
    }
}
