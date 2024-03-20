package com.sh.year.domain.goal.rule.domain;


import com.sh.year.domain.goal.goal.api.dto.req.RuleReqDto;
import com.sh.year.domain.goal.goal.domain.Goal;
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
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    private int routine; // 매일 : 1, 매주 : 2, 매월 : 3
    private LocalTime timeAt; // 시간 -> 매일일 경우 ex)18:00
    private String contents;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")
    private Goal goal;

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<RuleWeeklyDates> ruleWeeklyDatesList = new ArrayList<>();

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    private List<RuleMonthlyDates> ruleMonthlyDatesList = new ArrayList<>();

//    @Builder
//    private Rule(int routine, LocalTime timeAt, String contents) {
//        this.routine = routine;
//        this.timeAt = timeAt;
//        this.contents = contents;
//    }

    @Builder
    private Rule(int routine, LocalTime timeAt, String contents, Goal goal) {
        this.routine = routine;
        this.timeAt = timeAt;
        this.contents = contents;
        this.goal = goal;
    }

    /**
     * 생성
     */
//    public static Rule createRule(RuleReqDto ruleReqDto){
//
//        return Rule.builder()
//                .routine(ruleReqDto.getRoutine())
//                .timeAt(ruleReqDto.getTimeAt())
//                .contents(ruleReqDto.getContents())
//                .build();
//    }
    public static Rule createRule(RuleReqDto ruleReqDto, Goal goal){

        return Rule.builder()
                .routine(ruleReqDto.getRoutine())
                .timeAt(ruleReqDto.getTimeAt())
                .contents(ruleReqDto.getContents())
                .goal(goal)
                .build();
    }

    /**
     * 수정
     * 매일 -> 매주
     * 매일 -> 매월
     *
     * 매주 -> 매일
     * 매주 -> 매월
     *
     * 매월 -> 매일
     * 매월 -> 매주
     *
     * 흠...쓰바
     */
    public void updateRule(RuleReqDto reqDto) {
        this.routine = routine;
        this.timeAt = timeAt;
        this.contents = contents;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }


    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addWeeklyDates(RuleWeeklyDates ruleWeeklyDates){
        if(ruleWeeklyDates.getRule() != null){
            ruleWeeklyDates.getRule().getRuleWeeklyDatesList().remove(ruleWeeklyDates);
        }

        ruleWeeklyDates.setRule(this);
        this.ruleWeeklyDatesList.add(ruleWeeklyDates);
    }

    public void addMonthlyDates(RuleMonthlyDates ruleMonthlyDates) {
        if (ruleMonthlyDates.getRule() != null) {
            ruleMonthlyDates.getRule().getRuleMonthlyDatesList().remove(ruleMonthlyDates);
        }

        ruleMonthlyDates.setRule(this);
        this.ruleMonthlyDatesList.add(ruleMonthlyDates);
    }


}
