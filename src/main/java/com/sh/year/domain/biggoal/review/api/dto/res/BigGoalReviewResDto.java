package com.sh.year.domain.biggoal.review.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.biggoal.review.domain.BigGoalReview;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.review.domain.SmallGoalReview;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalReviewResDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;
    private String contents;
    private int starRating;

    public BigGoalReviewResDto(BigGoalReview bigGoalReview) {
        this.createdAt = LocalDate.from(bigGoalReview.getCreatedAt());
        this.contents = bigGoalReview.getContents();
        this.starRating = bigGoalReview.getStarRating();
    }

}
