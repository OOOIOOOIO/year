package com.sh.year.domain.goal.goal.api.dto.req;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleWeeklyReqDto {

    private int dates;
}
