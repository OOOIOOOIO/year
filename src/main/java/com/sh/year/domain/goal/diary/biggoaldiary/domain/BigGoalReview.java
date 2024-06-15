package com.sh.year.domain.goal.diary.biggoaldiary.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.diary.biggoaldiary.api.dto.BigGoalReviewReqDto;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bigGoalReviewId;
    private String contents;
    private double starRating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigGoalId")
    private BigGoal bigGoal;


    @Builder
    private BigGoalReview(String contents, double starRating) {
        this.contents = contents;
        this.starRating = starRating;
    }

    public static BigGoalReview createBigGoalReview(BigGoalReviewReqDto bigGoalReviewReqDto) {
        return BigGoalReview.builder()
                .contents(bigGoalReviewReqDto.getContents())
                .starRating(bigGoalReviewReqDto.getStarRating())

                .build();
    }

    public void setBigGoal(BigGoal bigGoal) {
        this.bigGoal = bigGoal;
    }

    public void updateBigGoalReview(BigGoalReviewReqDto bigGoalReviewReqDto){
        this.contents = bigGoalReviewReqDto.getContents();
        this.starRating = bigGoalReviewReqDto.getStarRating();
    }


}
