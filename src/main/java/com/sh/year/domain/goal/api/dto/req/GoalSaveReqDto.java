package com.sh.year.domain.goal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoalSaveReqDto {

    private String title;
    private String contents;
    private String icon;
    private String visualization;
    private int shareStatus;
    private int goalStatus;
    private RuleSaveReqDto ruleSaveReqDto;

}
