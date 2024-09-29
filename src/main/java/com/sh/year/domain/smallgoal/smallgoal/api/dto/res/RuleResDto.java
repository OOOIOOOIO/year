package com.sh.year.domain.smallgoal.smallgoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sh.year.api.main.controller.dto.res.TodayAlertSmallGoalInterface;
import com.sh.year.domain.rule.rule.domain.Rule;
import com.sh.year.domain.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    private LocalTime timeAt;
    private String contents;

//    private List<RuleRepeatDayDto> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날
    private List<Integer> ruleRepeatList; // week -> 1 : 일요일, 7 : 토요일 /  month -> if) -1일 경우 마지막 날
    private int completeStatus;
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

    public RuleResDto(TodayAlertSmallGoalInterface todayAlertSmallGoalInterface, Rule rule) {
        this.ruleId = todayAlertSmallGoalInterface.getRuleId();
        this.routine = todayAlertSmallGoalInterface.getRoutine();
        this.timeAt = todayAlertSmallGoalInterface.getTimeAt();
        this.contents = todayAlertSmallGoalInterface.getContents();
        this.ruleRepeatList = rule.getRuleRepeatDayList().stream()// lazy
                .map((o1) -> o1.getDay())
                .collect(Collectors.toList());

        this.ruleCompleteInfoDtoList = rule.getRuleCompleteInfoList().stream() // lazy
                .map(RuleCompleteInfoDto::new)
                .collect(Collectors.toList());

    }

    public void setCompleteStatus(int completeStatus) {
        this.completeStatus = completeStatus;
    }

}
