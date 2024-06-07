package com.sh.year.domain.goal.goal.smallgoal.api.dto.res;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryResDto {

    private Long diaryId;
    private String contents;
}
