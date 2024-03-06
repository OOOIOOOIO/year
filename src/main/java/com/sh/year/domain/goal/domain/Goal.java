package com.sh.year.domain.goal.domain;

import com.sh.year.domain.diary.domain.Diary;
import com.sh.year.domain.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToOne(mappedBy = "goal")
    private Rule rule;

    @OneToOne(mappedBy = "goal")
    private Diary diary;


    /**
     * 양방향 연관관계, cascade 유의
     */
    public void setUsers(Users users) {
        this.users = users;
    }
}
