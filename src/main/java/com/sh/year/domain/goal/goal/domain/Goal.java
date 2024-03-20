package com.sh.year.domain.goal.goal.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.diary.domain.Diary;
import com.sh.year.domain.goal.goal.api.dto.req.GoalReqDto;
import com.sh.year.domain.goal.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;
    private String title;
    private String contents;
    private String icon;
    private String visualization;
    private LocalDate endDate;
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


    @Builder
    private Goal(String title, String contents, String icon, String visualization, LocalDate endDate, ShareStatus shareStatus, GoalStatus goalStatus, Users users) {
        this.title = title;
        this.contents = contents;
        this.icon = icon;
        this.visualization = visualization;
        this.endDate = endDate;
        this.shareStatus = shareStatus;
        this.goalStatus = goalStatus;
        this.users = users;
    }

    /**
     * 생성
     */
    public static Goal createGoal(GoalReqDto goalReqDto, Users users){

        return Goal.builder()
                .title(goalReqDto.getTitle())
                .contents(goalReqDto.getContents())
                .icon(goalReqDto.getIcon())
                .visualization(goalReqDto.getVisualization())
                .endDate(goalReqDto.getEndDate())
                .shareStatus(goalReqDto.getShareStatus() == 0 ? ShareStatus.OFF : ShareStatus.ON)
                .goalStatus(goalReqDto.getGoalStatus() == 0 ? GoalStatus.FAIL : GoalStatus.COMP)
                .users(users)
                .build();
    }

    /**
     * 수정
     */
    public void updateGoal(GoalReqDto goalReqDto){
        this.title = goalReqDto.getTitle();
        this.contents = goalReqDto.getContents();
        this.icon = goalReqDto.getIcon();
        this.visualization = goalReqDto.getVisualization();
        this.endDate = goalReqDto.getEndDate();
        this.shareStatus = goalReqDto.getShareStatus() == 0 ? ShareStatus.OFF : ShareStatus.ON;
        this.goalStatus = goalReqDto.getGoalStatus() == 0 ? GoalStatus.FAIL : GoalStatus.COMP;
    }


    /**
     * 양방향 연관관계, cascade 유의
     */
    public void setUsers(Users users) {
        this.users = users;
    }


}
