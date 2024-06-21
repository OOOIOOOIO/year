package com.sh.year.domain.goal.goal.biggoal.api.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.RuleResDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BigGoalResDto {

    private String title;
    private String contents;
    private String category;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;
    private int shareStatus; // 0 : 공유 안함 / 1 : 공유 함
    private int completeStatus; // 0 : 실패 / 1 : 성공
    private List<SmallGoalResDto> smallGoalResDtoList;
    private Integer progress;
    private Integer smallGoalCnt;


    public BigGoalResDto(BigGoal bigGoal) {
        this.title = bigGoal.getTitle();
        this.contents = bigGoal.getContents();
        this.category = bigGoal.getCategory();
        this.endDate = bigGoal.getEndDate();
        this.shareStatus = bigGoal.getShareStatus().equals("ON") ? 1 : 0;
        this.completeStatus = bigGoal.getCompleteStatus().equals("COMP") ? 1 : 0;

    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void setSmallGoalResDtoList(List<SmallGoalResDto> smallGoalResDtoList){

        this.smallGoalResDtoList = smallGoalResDtoList;
    }
    public void setSmallGoalCnt(Integer smallGoalCnt) {
        this.smallGoalCnt = smallGoalCnt;
    }

}
