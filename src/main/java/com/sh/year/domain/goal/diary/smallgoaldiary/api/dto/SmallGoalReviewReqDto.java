package com.sh.year.domain.goal.diary.smallgoaldiary.api.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReviewReqDto {
    private String contents;
    private double starRating;

}
