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

    @OneToMany(mappedBy = "rule", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    private List<RuleRepeatDates> repeatDatesList = new ArrayList<>();

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
     *
     *  1 -> 2, 3
     *
     *  2 -> 1
     *  2 -> 3
     *
     *  3 -> 1
     *  3 -> 2
     *
     */
    public void updateRule(RuleReqDto ruleReqDto) {
        this.routine = ruleReqDto.getRoutine();
        this.timeAt = ruleReqDto.getTimeAt();
        this.contents = ruleReqDto.getContents();

    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }


    /**
     * 양방향 연관관계, cascade 유의
     */
    public void addRepeatDates(RuleRepeatDates repeatDates){
        if(repeatDates.getRule() != null){
            repeatDates.getRule().getRepeatDatesList().remove(repeatDates);
        }

        repeatDates.setRule(this);
        this.repeatDatesList.add(repeatDates);
    }



}
