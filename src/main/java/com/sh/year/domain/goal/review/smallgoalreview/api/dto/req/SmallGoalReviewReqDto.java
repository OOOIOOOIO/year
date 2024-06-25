package com.sh.year.domain.goal.review.smallgoalreview.api.dto.req;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReviewReqDto {
    private String contents;
    private int starRating;

}
