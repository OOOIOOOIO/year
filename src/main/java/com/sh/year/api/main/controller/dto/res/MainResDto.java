package com.sh.year.api.main.controller.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MainResDto {

    private String totalProgress;
    private List<BigGoalMainResDto> bigGoalMainResDtoList;
    private List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList;
    private List<DelayRuleResDto> delayRuleResDtoList;


    public MainResDto(List<BigGoalMainResDto> bigGoalMainResDtoList,
                      List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList,
                      List<DelayRuleResDto> delayRuleResDtoList) {
        this.bigGoalMainResDtoList = bigGoalMainResDtoList;
        this.smallGoalListForTodayAlertResDtoList = smallGoalListForTodayAlertResDtoList;
        this.delayRuleResDtoList = delayRuleResDtoList;
    }

    public void setTotalProgress(float totalProgress) {
        this.totalProgress = String.format("%.5f", totalProgress);
    }

}
