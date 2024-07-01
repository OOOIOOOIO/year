package com.sh.year.domain.goal.goal.smallgoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

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
    private Integer progress;

    public SmallGoalResDto(SmallGoal smallGoal) {
        this.smallGoalId = smallGoal.getSmallGoalId();
        this.title = smallGoal.getTitle();
        this.icon = smallGoal.getIcon();
        this.endDate = smallGoal.getEndDate();
        this.completeStatus = smallGoal.getCompleteStatus().equals(CompleteStatus.COMP) ? 1 : 0;
        this.ruleResDto = new RuleResDto(smallGoal.getRule());
        this.progress = 0;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
