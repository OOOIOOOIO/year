package com.sh.year.domain.goal.review.smallgoalreview.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoalReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smallGoalReviewId;
    private String contents;
    private int starRating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smallGoalId")
    private SmallGoal smallGoal;

    @Builder
    private SmallGoalReview(String contents, int starRating) {
        this.contents = contents;
        this.starRating = starRating;
    }

    public static SmallGoalReview createSmallGoalReview(SmallGoalReviewReqDto smallGoalReviewReqDto){
        return SmallGoalReview.builder()
                .contents(smallGoalReviewReqDto.getContents())
                .starRating(smallGoalReviewReqDto.getStarRating())
                .build();
    }

    public void setSmallGoal(SmallGoal smallGoal) {
        this.smallGoal = smallGoal;
    }

    public void updateSmallGoalReview(SmallGoalReviewReqDto smallGoalReviewReqDto){
        this.contents = smallGoalReviewReqDto.getContents();
        this.starRating = smallGoalReviewReqDto.getStarRating();
    }


}
