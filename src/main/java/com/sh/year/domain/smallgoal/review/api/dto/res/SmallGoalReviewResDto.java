package com.sh.year.domain.smallgoal.review.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.review.domain.SmallGoalReview;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SmallGoalReviewResDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;
    private String contents;
    private int completeStatus;
    private int starRating;

    public SmallGoalReviewResDto(SmallGoalReview smallGoalReview) {
        this.createdAt = LocalDate.from(smallGoalReview.getCreatedAt());
        this.contents = smallGoalReview.getContents();
        this.starRating = smallGoalReview.getStarRating();


        if(smallGoalReview.getCompleteStatus().equals(CompleteStatus.FAIL)){
            this.completeStatus = -1;
        }
        else if(smallGoalReview.getCompleteStatus().equals(CompleteStatus.DELAY)){
            this.completeStatus = 0;
        }
        else{
            this.completeStatus = 1;
        }
    }
}
