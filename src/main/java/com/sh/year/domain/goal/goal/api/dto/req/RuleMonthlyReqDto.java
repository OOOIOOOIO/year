package com.sh.year.domain.goal.goal.api.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleMonthlyReqDto {

    private LocalDate days;

}
