package com.sh.year.domain.biggoal.biggoal.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalCompleteYearAndProgressResDto {

    private int year;
    private String progress;

    public BigGoalCompleteYearAndProgressResDto(int year) {
        this.year = year;
    }

    public void setProgress(float progress) {
        this.progress = String.format("%.5f", progress);
    }
}
