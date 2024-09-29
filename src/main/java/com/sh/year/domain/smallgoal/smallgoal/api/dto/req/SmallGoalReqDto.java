package com.sh.year.domain.smallgoal.smallgoal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReqDto {

    private String title;
    private String icon;
    private LocalDate endDate;
    private int completeStatus; // 0 : 실패 / 1 : 성공
    private RuleReqDto ruleReqDto;

}
