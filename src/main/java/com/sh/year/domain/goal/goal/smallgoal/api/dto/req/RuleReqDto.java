package com.sh.year.domain.goal.goal.smallgoal.api.dto.req;

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
    private List<Integer> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날



}
