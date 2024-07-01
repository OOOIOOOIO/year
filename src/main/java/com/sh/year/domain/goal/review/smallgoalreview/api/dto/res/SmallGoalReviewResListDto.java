package com.sh.year.domain.goal.review.smallgoalreview.api.dto.res;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReviewResListDto {
    private List<SmallGoalReviewResDto> smallGoalReviewResDto;

    public SmallGoalReviewResListDto(List<SmallGoalReviewResDto> smallGoalReviewResDto) {
        this.smallGoalReviewResDto = smallGoalReviewResDto;
    }
}
