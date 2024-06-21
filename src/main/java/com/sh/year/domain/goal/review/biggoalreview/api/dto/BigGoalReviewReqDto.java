package com.sh.year.domain.goal.review.biggoalreview.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalReviewReqDto {
    private String contents;
    private int starRating;

}
