package com.sh.year.domain.goal.api;

import com.sh.year.domain.goal.application.GoalService;
import com.sh.year.global.resolver.tokeninfo.TokenFromHeader;
import com.sh.year.global.resolver.tokeninfo.TokenFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal")
public class GoalController {

    private final GoalService goalService;

    /**
     * 상세목표 보기
     */
    @GetMapping("/info/{goalId}")
    public void getGoalInfo(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 저장
     */
    @PostMapping("/}")
    public void saveGoal(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 수정
     */
    @PutMapping("/{goalId}")
    public void updateGoal(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 삭제
     */
    @DeleteMapping("/{goalId}")
    public void deleteGoal(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 목표 공유 설정
     */
    @PutMapping("/share/{goalId}")
    public void updateShareStatus(@PathVariable(value = "goalId") Long goalId){

    }

    /**
     * 내 목표들 보기
     */
    @GetMapping("/list")
    public void getGoalList(@TokenFromHeader TokenFromHeaderDto userInfoFromTokenDto){

    }



}
