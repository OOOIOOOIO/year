package com.sh.year.domain.goal.goal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoalReqDto {

    private String title;
    private String contents;
    private String icon;
    private String visualization;
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int goalStatus; // 0 : 실패 / 1 : 성공
    private RuleReqDto ruleReqDto;



}
