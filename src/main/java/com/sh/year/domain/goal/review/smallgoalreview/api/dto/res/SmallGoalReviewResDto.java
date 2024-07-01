package com.sh.year.domain.goal.review.smallgoalreview.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class SmallGoalReviewResDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;
    private String contents;
    private int starRating;

    public SmallGoalReviewResDto(SmallGoalReview smallGoalReview) {
        this.createdAt = LocalDate.from(smallGoalReview.getCreatedAt());
        this.contents = smallGoalReview.getContents();
        this.starRating = smallGoalReview.getStarRating();
    }
}
