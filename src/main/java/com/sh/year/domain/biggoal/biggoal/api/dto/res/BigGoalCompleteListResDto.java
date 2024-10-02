package com.sh.year.domain.biggoal.biggoal.api.dto.res;

import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalCompleteListResDto {

    private int currentYearBigGoalCnt;
    private List<BigGoalMainResDto> bigGoalMainResDtoList;

    public BigGoalCompleteListResDto(int currentYearBigGoalCnt, List<BigGoalMainResDto> bigGoalMainResDtoList) {
        this.currentYearBigGoalCnt = currentYearBigGoalCnt;
        this.bigGoalMainResDtoList = bigGoalMainResDtoList;
    }
}
