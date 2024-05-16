package com.sh.year.domain.goal.goal.smallgoal.api.dto.res;

import com.sh.year.domain.goal.rule.rule.domain.Rule;

import java.time.LocalTime;

public class RuleResDto {

    private int routine; // 매일 : 1, 매주 : 2, 매월 : 3
    private LocalTime timeAt;
    private String contents;

    // 오늘 알림을 줘야하는지에 대한 유무?
    private int alarmState; // 1 : ON / 0 : OFF
//    private List<Integer> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날


    public RuleResDto(Rule rule, int alarmState) {
        this.routine = rule.getRoutine();
        this.timeAt = rule.getTimeAt();
        this.contents = rule.getContents();
        this.alarmState = alarmState;
    }

}
