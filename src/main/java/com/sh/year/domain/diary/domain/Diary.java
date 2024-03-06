package com.sh.year.domain.diary.domain;

import com.sh.year.domain.goal.domain.Goal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diaryId;
    private String contents;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goalId")
    private Goal goal;

}
