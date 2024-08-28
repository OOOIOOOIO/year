package com.sh.year.domain.goal.review.smallgoalreview.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
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

    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus; // 0 : delay / 1 : comp / -1 : fail

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    private SmallGoalReview(String contents, int completeStatus, int starRating) {
        this.contents = contents;
        this.starRating = starRating;

        if(completeStatus == -1){
            this.completeStatus = CompleteStatus.FAIL;
        }
        else if(completeStatus == 0){
            this.completeStatus = CompleteStatus.DELAY;
        }
        else{
            this.completeStatus = CompleteStatus.COMP;
        }

    }

    public static SmallGoalReview createSmallGoalReview(SmallGoalReviewReqDto smallGoalReviewReqDto){
        return SmallGoalReview.builder()
                .contents(smallGoalReviewReqDto.getContents())
                .completeStatus(smallGoalReviewReqDto.getCompleteStatus())
                .starRating(smallGoalReviewReqDto.getStarRating())
                .build();
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void updateSmallGoalReview(SmallGoalReviewReqDto smallGoalReviewReqDto){
        this.contents = smallGoalReviewReqDto.getContents();
        this.starRating = smallGoalReviewReqDto.getStarRating();
    }


}
