package com.sh.year.domain.goal.goal.api;

import com.sh.year.domain.goal.goal.api.dto.req.GoalReqDto;
import com.sh.year.domain.goal.goal.application.GoalService;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/save")
    public ResponseEntity<Long> saveGoal(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                           @RequestBody GoalReqDto goalReqDto){

        Long goalId = goalService.saveGoal(userInfoFromHeaderDto, goalReqDto);

        return new ResponseEntity<>(goalId, HttpStatus.OK);

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
    public void getGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){

    }



}
