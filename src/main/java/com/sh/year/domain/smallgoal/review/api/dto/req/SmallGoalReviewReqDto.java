package com.sh.year.domain.smallgoal.review.api.dto.req;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReviewReqDto {
    private String contents;
    private int completeStatus;
    private int starRating;

}
