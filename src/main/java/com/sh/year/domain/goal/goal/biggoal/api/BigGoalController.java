package com.sh.year.domain.goal.goal.biggoal.api;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
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

@Tag(name = "Big Goal", description = "큰목표 API")
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
            summary = "큰목표 상세조회 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 상세조회에 성공하였습니다."
    )
    @GetMapping("/{bigGoalId}")
    public ResponseEntity<BigGoalResDto> getGoalInfo(@PathVariable(value = "bigGoalId") Long bigGoalId){

        BigGoalResDto bigGoalInfo = bigGoalService.getBigGoalInfo(bigGoalId);

        return new ResponseEntity<>(bigGoalInfo, HttpStatus.OK);
    }

    /**
     * 큰목표 저장
     */
    @Operation(
            summary = "큰목표 저장 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 저장에 성공하였습니다."
    )
    @PostMapping("")
    public ResponseEntity<Long> saveGoal(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                           @RequestBody BigGoalReqDto bigGoalReqDto){

        Long goalId = bigGoalService.saveBigGoal(userInfoFromHeaderDto, bigGoalReqDto);

        return new ResponseEntity<>(goalId, HttpStatus.OK);

    }


    /**
     * 큰목표 수정
     */
    @Operation(
            summary = "큰목표 수정 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 수정에 성공하였습니다."
    )
    @PutMapping("/{bigGoalId}")
    public ResponseEntity<String> updateGoal(@PathVariable(value = "bigGoalId") Long bigGoalId,
                           @RequestBody BigGoalReqDto bigGoalReqDto){

        bigGoalService.updateGoal(bigGoalId, bigGoalReqDto);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 삭제
     */
    @Operation(
            summary = "큰목표 삭제 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 삭제에 성공하였습니다."
    )
    @DeleteMapping("/{bigGoalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.deleteGoal(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 공유여부 변경
     */
    @Operation(
            summary = "큰목표 공유여부 변경 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 공유여부 변경에 성공하였습니다."
    )
    @PutMapping("/share/{bigGoalId}")
    public ResponseEntity<String> updateShareStatus(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.updateShareStatus(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 큰목표 달성여부 변경
     */
    @Operation(
            summary = "큰목표 달성여부 변경 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 달성여부 변경에 성공하였습니다."
    )
    @PutMapping("/comp/{bigGoalId}")
    public ResponseEntity<String> updateCompleteStatus(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.updateCompleteStatus(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }


    /**
     * 내 큰목표들 보기
     */
    @Operation(
            summary = "큰목표 리스트로 조회 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 리스트로 조회에 성공하였습니다."
    )
    @GetMapping("/list")
    public void getGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){

    }



}
