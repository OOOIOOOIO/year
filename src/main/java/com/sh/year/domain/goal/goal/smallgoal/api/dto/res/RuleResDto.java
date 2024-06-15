package com.sh.year.domain.goal.goal.smallgoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleResDto {
    private Long ruleId;
    private int routine; // 매일 : 1, 매주 : 2, 매월 : 3
    private LocalTime timeAt;
    private String contents;

//    private List<RuleRepeatDayDto> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날
    private List<Integer> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날
    @JsonIgnore
    private List<RuleCompleteInfoDto> ruleCompleteInfoDtoList;


    public RuleResDto(Rule rule) {
        this.ruleId = rule.getRuleId();
        this.routine = rule.getRoutine();
        this.timeAt = rule.getTimeAt();
        this.contents = rule.getContents();
        this.ruleRepeatList = rule.getRuleRepeatDayList().stream()// lazy
                .map((o1) -> o1.getDay())
                .collect(Collectors.toList());

        this.ruleCompleteInfoDtoList = rule.getRuleCompleteInfoList().stream() // lazy
                .map(RuleCompleteInfoDto::new)
                .collect(Collectors.toList());

    }

}
