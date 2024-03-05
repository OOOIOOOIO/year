package com.sh.year.domain.rule.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    private int routine; //매일 : 1, 매주 : 2, 매월 : 3
    private String time; // 시간 -> 매일일 경우 ex)18:00
    private LocalDate day; // 요일 -> 매주일 경우 ex)2024-01-01
    private LocalDate date; //날짜 -> 매월일 경우 ex)2024-01-01
//    @OneToOne
    private String goal_id;


}
