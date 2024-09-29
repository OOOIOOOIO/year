package com.sh.year.domain.biggoal.biggoal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalReqDto {

    private String title;
    private String contents;
    private String category;
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int completeStatus; // 0 : 실패 / 1 : 성공



}
