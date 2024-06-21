package com.sh.year.domain.goal.review.smallgoalreview.api.dto.res;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReviewResListDto {
    private SmallGoalReviewResDto smallGoalReviewResDto;

    public SmallGoalReviewResListDto(SmallGoalReviewResDto smallGoalReviewResDto) {
        this.smallGoalReviewResDto = smallGoalReviewResDto;
    }
}
