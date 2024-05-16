package com.sh.year.domain.goal.goal.biggoal.api;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.domain.goal.goal.biggoal.application.BigGoalService;
import com.sh.year.global.common.ResponseConst;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "BigGoal", description = "큰목표 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal/biggoal")
public class BigGoalController {

    private final BigGoalService bigGoalService;

    /**
     * 큰목표 상세보기
     */
    @Operation(
            summary = "큰목표 API",
            description = "큰목표 상세조회."
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 상세조회에 성공하였습니다."
    )
    @GetMapping("/{goalId}")
    public void getGoalInfo(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.getBigGoalInfo(bigGoalId);

    }

    /**
     * 큰목표 저장
     */
    @PostMapping("/")
    public ResponseEntity<Long> saveGoal(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                           @RequestBody BigGoalReqDto bigGoalReqDto){

        Long goalId = bigGoalService.saveBigGoal(userInfoFromHeaderDto, bigGoalReqDto);

        return new ResponseEntity<>(goalId, HttpStatus.OK);

    }


    /**
     * 큰목표 수정
     */
    @PutMapping("/{goalId}")
    public ResponseEntity<String> updateGoal(@PathVariable(value = "goalId") Long bigGoalId,
                           @RequestBody BigGoalReqDto bigGoalReqDto){

        bigGoalService.updateGoal(bigGoalId, bigGoalReqDto);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 삭제
     */
    @DeleteMapping("/{goalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable(value = "goalId") Long bigGoalId){

        bigGoalService.deleteGoal(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 공유여부 설정
     */
    @PutMapping("/share/{goalId}")
    public ResponseEntity<String> updateShareStatus(@PathVariable(value = "goalId") Long bigGoalId){

        bigGoalService.updateShareStatus(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 달성여부 설정
     */
    @PutMapping("/comp/{goalId}")
    public ResponseEntity<String> updateCompleteStatus(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.updateCompleteStatus(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }


    /**
     * 내 큰목표들 보기
     */
    @GetMapping("/list")
    public void getGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){

    }



}
