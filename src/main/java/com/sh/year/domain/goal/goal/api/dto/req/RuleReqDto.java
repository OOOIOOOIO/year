package com.sh.year.domain.goal.goal.api.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class RuleReqDto {

    private int routine; // 매일 : 1, 매주 : 2, 매월 : 3
    private LocalTime timeAt;
    private String contents;
    private List<RuleMonthlyReqDto> ruleMonthlyReqDtoList;
    private List<RuleWeeklyReqDto> ruleWeeklyReqDtoList;



}
