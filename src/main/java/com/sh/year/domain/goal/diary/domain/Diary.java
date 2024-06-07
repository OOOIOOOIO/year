package com.sh.year.domain.goal.diary.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smallGoalId")
    private SmallGoal smallGoal;

    public void setSmallGoal(SmallGoal smallGoal) {
        this.smallGoal = smallGoal;
    }


}
