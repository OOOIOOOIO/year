package com.sh.year.domain.goal.goal.biggoal.domain;

import com.sh.year.domain.common.BaseTimeEntity;
import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.user.domain.Users;
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
public class BigGoal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bigGoalId;
    private String title;
    private String contents;
    private String category;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ShareStatus shareStatus;
    @Enumerated(EnumType.STRING)
    private CompleteStatus completeStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @OneToMany(mappedBy = "bigGoal", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<SmallGoal> smallGoalList = new ArrayList<>();


    @Builder
    private BigGoal(String title, String contents, String category, LocalDate endDate, ShareStatus shareStatus, CompleteStatus completeStatus, Users users) {
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.endDate = endDate;
        this.shareStatus = shareStatus;
        this.completeStatus = completeStatus;
        this.users = users;
    }

    /**
     * 생성
     */
    public static BigGoal createBigGoal(BigGoalReqDto bigGoalReqDto, Users users){

        return BigGoal.builder()
                .title(bigGoalReqDto.getTitle())
                .contents(bigGoalReqDto.getContents())
                .category(bigGoalReqDto.getCategory())
                .endDate(bigGoalReqDto.getEndDate())
                .shareStatus(bigGoalReqDto.getShareStatus() == 0 ? ShareStatus.OFF : ShareStatus.ON)
                .completeStatus(bigGoalReqDto.getCompleteStatus() == 0 ? CompleteStatus.FAIL : CompleteStatus.COMP)
                .users(users)
                .build();
    }

    /**
     * 수정
     */
    public void updateBigGoal(BigGoalReqDto bigGoalReqDto){
        this.title = bigGoalReqDto.getTitle();
        this.contents = bigGoalReqDto.getContents();
        this.category = bigGoalReqDto.getCategory();
        this.endDate = bigGoalReqDto.getEndDate();
        this.shareStatus = bigGoalReqDto.getShareStatus() == 0 ? ShareStatus.OFF : ShareStatus.ON;
        this.completeStatus = bigGoalReqDto.getCompleteStatus() == 0 ? CompleteStatus.FAIL : CompleteStatus.COMP;

    }

    public void updateShareStatus(ShareStatus shareStatus){
        this.shareStatus = shareStatus.equals(ShareStatus.ON) ? ShareStatus.OFF : ShareStatus.ON;
    }


    public void updateCompleteStatus(CompleteStatus completeStatus) {
        this.completeStatus = completeStatus.equals(CompleteStatus.COMP) ? CompleteStatus.FAIL : CompleteStatus.COMP;

    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void addSmallGoal(SmallGoal smallGoal) {
        if(smallGoal.getBigGoal() != null){
            smallGoal.getBigGoal().getSmallGoalList().remove(smallGoal);
        }

        smallGoal.setBigGoal(this);
        this.smallGoalList.add(smallGoal);
    }


}
