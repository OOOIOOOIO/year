package com.sh.year.api.main.controller.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DelayGoalListResDto {
    private List<DelayGoalResDto> delayGoalList;

    public DelayGoalListResDto(List<DelayGoalResDto> delayGoalList) {
        this.delayGoalList = delayGoalList;
    }
}
