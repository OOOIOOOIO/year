package com.sh.year.domain.smallgoal.smallgoal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalUpdateReqDto {

    private String title;
    private String icon;
    private LocalDate endDate;


}
