package com.sh.year.domain.goal.goal.smallgoal.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalUpdateReqDto;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallGoal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long smallGoalId;
    private String title;
    private String icon;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bigGoalId")
    private BigGoal bigGoal;

    @OneToOne(mappedBy = "smallGoal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private Rule rule;

    @OneToMany(mappedBy = "smallGoal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SmallGoalReview> smallGoalReviewList = new ArrayList<>();


    @Builder
    private SmallGoal(String title, String icon, LocalDate endDate, CompleteStatus completeStatus, BigGoal bigGoal) {
        this.title = title;
        this.icon = icon;
        this.endDate = endDate;
        this.completeStatus = completeStatus;
        this.bigGoal = bigGoal;
    }

    public static SmallGoal createSmallGoal(SmallGoalReqDto smallGoalReqDto, BigGoal bigGoal){
        return SmallGoal.builder()
                .bigGoal(bigGoal)
                .title(smallGoalReqDto.getTitle())
                .icon(smallGoalReqDto.getIcon())
                .endDate(smallGoalReqDto.getEndDate())
                .completeStatus(smallGoalReqDto.getCompleteStatus() == 0 ? CompleteStatus.FAIL : CompleteStatus.COMP)
                .build();
    }

    public void updateSmallGoal(SmallGoalUpdateReqDto smallGoalUpdateReqDto){
        this.title = smallGoalUpdateReqDto.getTitle();
        this.icon = smallGoalUpdateReqDto.getIcon();
        this.endDate = smallGoalUpdateReqDto.getEndDate();
    }

    public void updateCompleteStatus(CompleteStatus completeStatus){
        this.completeStatus = completeStatus.equals(CompleteStatus.COMP) ? CompleteStatus.FAIL : CompleteStatus.COMP;
    }

    public void setBigGoal(BigGoal bigGoal) {
        this.bigGoal = bigGoal;
    }

    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addRule(Rule rule){
        rule.setSmallGoal(this);
        this.rule = rule;

    }

    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addSmallGoalReview(SmallGoalReview smallGoalReview){
        if(smallGoalReview.getSmallGoal() != null){
            smallGoalReview.getSmallGoal().getSmallGoalReviewList().remove(smallGoalReview);
        }

        smallGoalReview.setSmallGoal(this);
        this.smallGoalReviewList.add(smallGoalReview);
    }




}
