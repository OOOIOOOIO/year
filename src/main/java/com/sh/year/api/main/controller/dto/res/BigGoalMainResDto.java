package com.sh.year.api.main.controller.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalMainResDto {

    private Long bigGoalId;
    private String title;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private String progress;
    private Integer smallGoalCnt;

    public BigGoalMainResDto(BigGoal bigGoal) {
        this.bigGoalId = bigGoal.getBigGoalId();
        this.title = bigGoal.getTitle();
        this.category = bigGoal.getCategory();
        this.endDate = bigGoal.getEndDate();
    }

    public void setProgress(float progress) {
        this.progress = String.format("%.5f", progress);
    }


    public void setSmallGoalCnt(Integer smallGoalCnt) {
        this.smallGoalCnt = smallGoalCnt;
    }

}
