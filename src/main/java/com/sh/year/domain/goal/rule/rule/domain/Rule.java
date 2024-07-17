package com.sh.year.domain.goal.rule.rule.domain;


import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.goal.delayGoal.domain.DelayGoal;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.RuleReqDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.rule.rulealertinfo.domain.RuleAlertInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.goal.rule.rulerepeatday.domain.RuleRepeatDay;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    private String contents;
    private int routine; // 매일 : 1, 매주 : 2, 매월 : 3
    private LocalTime timeAt; // 시간 -> 매일일 경우 ex)18:00

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smallGoalId")
    private SmallGoal smallGoal;

    /**
     * hmm
     */
    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RuleCompleteInfo> ruleCompleteInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RuleRepeatDay> ruleRepeatDayList = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<RuleAlertInfo> ruleAlertInfoList = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<DelayGoal> delayGoalList = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SmallGoalReview> smallGoalReviewList = new ArrayList<>();



    @Builder
    private Rule(int routine, LocalTime timeAt, String contents, SmallGoal smallGoal) {
        this.routine = routine;
        this.timeAt = timeAt;
        this.contents = contents;
        this.smallGoal = smallGoal;
    }


    public static Rule createRule(RuleReqDto ruleReqDto, SmallGoal smallGoal){

        return Rule.builder()
                .routine(ruleReqDto.getRoutine())
                .timeAt(ruleReqDto.getTimeAt())
                .contents(ruleReqDto.getContents())
                .smallGoal(smallGoal)
                .build();
    }

    public void updateRule(RuleReqDto ruleReqDto) {
        this.routine = ruleReqDto.getRoutine();
        this.timeAt = ruleReqDto.getTimeAt();
        this.contents = ruleReqDto.getContents();

    }


    public void setSmallGoal(SmallGoal smallGoal) {
        this.smallGoal = smallGoal;
    }


    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addCompleteInfo(RuleCompleteInfo ruleCompleteInfo){
        if(ruleCompleteInfo.getRule() != null){
            ruleCompleteInfo.getRule().getRuleCompleteInfoList().remove(ruleCompleteInfo);
        }

        ruleCompleteInfo.setRule(this);
        this.ruleCompleteInfoList.add(ruleCompleteInfo);
    }

    public void addRuleRepeatDay(RuleRepeatDay ruleRepeatDay) {
        if(ruleRepeatDay.getRule() != null){
            ruleRepeatDay.getRule().getRuleRepeatDayList().remove(ruleRepeatDay);
        }

        ruleRepeatDay.setRule(this);
        this.ruleRepeatDayList.add(ruleRepeatDay);
    }

    public void addAlertInfo(RuleAlertInfo ruleAlertInfo){
        if(ruleAlertInfo.getRule() != null){
            ruleAlertInfo.getRule().getRuleCompleteInfoList().remove(ruleAlertInfo);
        }

        ruleAlertInfo.setRule(this);
        this.ruleAlertInfoList.add(ruleAlertInfo);
    }

    public void addDelayGoal(DelayGoal delayGoal){
        if(delayGoal.getRule() != null){
            delayGoal.getRule().getDelayGoalList().remove(delayGoal);
        }
        delayGoal.setRule(this);

        this.delayGoalList.add(delayGoal);
    }


    public void addSmallGoalReview(SmallGoalReview smallGoalReview){
        if(smallGoalReview.getRule() != null){
            smallGoalReview.getRule().getSmallGoalReviewList().remove(smallGoalReview);
        }

        smallGoalReview.setRule(this);
        this.smallGoalReviewList.add(smallGoalReview);
    }


    public void updateContents(String contents) {
        this.contents = contents;
    }
}
