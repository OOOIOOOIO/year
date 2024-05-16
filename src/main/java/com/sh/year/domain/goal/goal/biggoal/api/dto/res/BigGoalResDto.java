package com.sh.year.domain.goal.goal.biggoal.api.dto.res;

import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.RuleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalResDto {

    private String title;
    private String contents;
    private String icon;
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int completeStatus; // 0 : 실패 / 1 : 성공
    private RuleResDto ruleResDto;
//    private List<>
    private Integer progress; // 목표 진행률, 만들어줘야함 smallgoal


    public BigGoalResDto(BigGoal bigGoal, RuleResDto ruleResDto, Integer progress) {
        this.title = bigGoal.getTitle();
        this.contents = bigGoal.getContents();
        this.icon = bigGoal.getCategory();
        this.endDate = bigGoal.getEndDate();
        this.shareStatus = bigGoal.getShareStatus().equals("ON") ? 1 : 0;
        this.completeStatus = bigGoal.getCompleteStatus().equals("COMP") ? 1 : 0;
        this.ruleResDto = ruleResDto;
        this.progress = progress;
    }
}
