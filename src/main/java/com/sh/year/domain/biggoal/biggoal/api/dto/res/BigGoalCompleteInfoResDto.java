package com.sh.year.domain.biggoal.biggoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.biggoal.biggoal.domain.ShareStatus;
import com.sh.year.domain.biggoal.review.api.dto.res.BigGoalReviewResDto;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.smallgoal.api.dto.res.SmallGoalResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalCompleteInfoResDto {

    private String title;
    private String contents;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int completeStatus; // 0 : 실패 / 1 : 성공
    private List<SmallGoalResDto> smallGoalResDtoList;
    private String progress;
    private Integer smallGoalCnt;

    private BigGoalReviewResDto bigGoalReviewResDto;


    public BigGoalCompleteInfoResDto(BigGoal bigGoal) {
        this.title = bigGoal.getTitle();
        this.contents = bigGoal.getContents();
        this.category = bigGoal.getCategory();
        this.endDate = bigGoal.getEndDate();
        this.shareStatus = bigGoal.getShareStatus().equals(ShareStatus.ON) ? 1 : 0;
        this.completeStatus = bigGoal.getCompleteStatus().equals(CompleteStatus.COMP) ? 1 : 0;

    }

    public void setProgress(float progress) {
        this.progress = String.format("%.5f", progress);
    }

    public void setSmallGoalResDtoList(List<SmallGoalResDto> smallGoalResDtoList){

        this.smallGoalResDtoList = smallGoalResDtoList;
    }
    public void setSmallGoalCnt(Integer smallGoalCnt) {
        this.smallGoalCnt = smallGoalCnt;
    }

    public void setBigGoalReviewResDto(BigGoalReviewResDto bigGoalReviewResDto) {
        this.bigGoalReviewResDto = bigGoalReviewResDto;
    }

}
