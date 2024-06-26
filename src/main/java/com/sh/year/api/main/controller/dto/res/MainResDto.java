package com.sh.year.api.main.controller.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainResDto {

    private List<BigGoalMainResDto> bigGoalMainResDtoList;
    private List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList;

    private List<DelayGoalResDto> delayGoalResDtoList;

    public MainResDto(List<BigGoalMainResDto> bigGoalMainResDtoList,
                      List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList,
                      List<DelayGoalResDto> delayGoalResDtoList) {
        this.bigGoalMainResDtoList = bigGoalMainResDtoList;
        this.smallGoalListForTodayAlertResDtoList = smallGoalListForTodayAlertResDtoList;
        this.delayGoalResDtoList = delayGoalResDtoList;
    }
}
