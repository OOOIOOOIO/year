package com.sh.year.domain.biggoal.biggoal.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalCompleteYearAndProgressListResDto {
    private List<BigGoalCompleteYearAndProgressResDto> bigGoalCompleteYearAndProgressResDtoList;
    private int currentYearCompleteBigGoalCnt;

    public BigGoalCompleteYearAndProgressListResDto(List<BigGoalCompleteYearAndProgressResDto> bigGoalCompleteYearAndProgressResDto, int currentYearCompleteBigGoalCnt) {
        this.bigGoalCompleteYearAndProgressResDtoList = bigGoalCompleteYearAndProgressResDto;
        this.currentYearCompleteBigGoalCnt = currentYearCompleteBigGoalCnt;
    }
}
