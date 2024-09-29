package com.sh.year.domain.biggoal.review.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalReviewReqDto {
    private String contents;
    private int starRating;

}
