package com.sh.year.domain.goal.goal.biggoal.api.dto.res;

import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalResDto {

    private String title;
    private String contents;
    private String category;
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int completeStatus; // 0 : 실패 / 1 : 성공
    private List<SmallGoalResDto> smallGoalResDtoList;
    private Integer progress; // 목표 진행률, 만들어줘야함 smallgoal
    private Integer smallGoalCnt;


    public BigGoalResDto(BigGoal bigGoal) {
        this.title = bigGoal.getTitle();
        this.contents = bigGoal.getContents();
        this.category = bigGoal.getCategory();
        this.endDate = bigGoal.getEndDate();
        this.shareStatus = bigGoal.getShareStatus().equals("ON") ? 1 : 0;
        this.completeStatus = bigGoal.getCompleteStatus().equals("COMP") ? 1 : 0;
        this.smallGoalResDtoList = bigGoal.getSmallGoalList().stream()
                                            .map(SmallGoalResDto::new)
                                            .collect(Collectors.toList());
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setSmallGoalCnt(Integer smallGoalCnt) {
        this.smallGoalCnt = smallGoalCnt;
    }
}
