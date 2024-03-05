package com.sh.year.domain.goal.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    private String title;
    private String contents;
    private String icon;
    private String visualization;
    @Enumerated(EnumType.STRING)
    private ShareStatus shareStatus;
    @Enumerated(EnumType.STRING)
    private GoalStatus goalStatus;
//    @ManyToOne()
    private Long user_id;




}
