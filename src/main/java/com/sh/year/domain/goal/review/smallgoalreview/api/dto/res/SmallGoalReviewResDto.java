package com.sh.year.domain.goal.review.smallgoalreview.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class SmallGoalReviewResDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate createdAt;
    private String contents;
    private int starRating;
}
