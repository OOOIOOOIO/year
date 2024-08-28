package com.sh.year.domain.goal.goal.smallgoal.api;

import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalUpdateReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import com.sh.year.domain.goal.goal.smallgoal.application.SmallGoalService;
import com.sh.year.global.common.ResponseConst;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeader;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Small Goal", description = "작은목표 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal/smallgoal")
public class SmallGoalController {

    private final SmallGoalService smallGoalService;

    /**
     * 작은목표 상세보기
     */
    @Operation(
            summary = "작은목표 상세조회 API",
            description = "작은목표 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 상세조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/{smallGoalId}")
    public ResponseEntity<SmallGoalResDto> getGoalInfo(@PathVariable(value = "smallGoalId") Long smallGoalId,
                                                       @UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        SmallGoalResDto smallGoalInfo = smallGoalService.getSmallGoalInfo(smallGoalId, userInfoFromHeaderDto);

        return new ResponseEntity<>(smallGoalInfo, HttpStatus.OK);

    }

    /**
     * 내 작은 목표들 리스트 보기
     */
    @Operation(
            summary = "작은목표 리스트로 조회 API",
            description = "작은목표 리스트 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 리스트로 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/{bigGoalId}/list")
    public ResponseEntity<List<SmallGoalResDto>> getGoalList(@PathVariable("bigGoalId") Long bigGoalId){

        List<SmallGoalResDto> smallGoalList = smallGoalService.getSmallGoalList(bigGoalId);

        return new ResponseEntity<>(smallGoalList, HttpStatus.OK);
    }

    /**
     * 작은목표 저장
     */
    @Operation(
            summary = "작은목표 저장 API",
            description = "작은목표 저장"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 저장에 성공하였습니다."
    )
    @LogTrace
    @PostMapping("/{bigGoalId}")
    public ResponseEntity<Long> saveGoal(@PathVariable(value = "bigGoalId") Long bigGoalId, @RequestBody SmallGoalReqDto smallGoalReqDto){

        Long goalId = smallGoalService.saveSmallGoal(bigGoalId, smallGoalReqDto);

        return new ResponseEntity<>(goalId, HttpStatus.OK);

    }


    /**
     * 작은목표 수정
     */
    @Operation(
            summary = "작은목표 수정 API",
            description = "작은목표 수정"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 수정에 성공하였습니다."
    )
    @LogTrace
    @PutMapping("/{smallGoalId}")
    public ResponseEntity<String> updateGoal(@PathVariable(value = "smallGoalId") Long smallGoalId, @RequestBody SmallGoalUpdateReqDto smallGoalUpdateReqDto){

        smallGoalService.updateSmallGoal(smallGoalId, smallGoalUpdateReqDto);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 작은목표 삭제
     */
    @Operation(
            summary = "작은목표 삭제 API",
            description = "작은목표 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 삭제에 성공하였습니다."
    )
    @LogTrace
    @DeleteMapping("/{smallGoalId}")
    public ResponseEntity<String> deleteGoal(@PathVariable(value = "smallGoalId") Long smallGoalId){

        smallGoalService.deleteSmallGoal(smallGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 작은목표 100% 달성시 달성여부 변경
     */
    @Operation(
            summary = "작은목표 100% 달성시 달성여부 변경 API",
            description = "작은목표 100% 달성 시 달성여부 변경"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 100% 달성시 달성여부 변경 성공하였습니다."
    )
    @LogTrace
    @PutMapping("/comp/{smallGoalId}")
    public ResponseEntity<String> updateShareStatus(@PathVariable(value = "smallGoalId") Long smallGoalId){

        smallGoalService.updateCompleteStatus(smallGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }

    /**
     * 작은목표 루틴 달성여부 변경
     */
    @Operation(
            summary = "작은목표 루틴 성공 처리 API",
            description = "작은목표 루틴 성공 처리"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 루틴 성공 처리에 성공하였습니다."
    )
    @LogTrace
    @PutMapping("/routine/comp/{ruleId}")
    public ResponseEntity<String> updateRuleCompleteInfo(@PathVariable("ruleId") Long ruleId){

        smallGoalService.updateRuleCompleteInfo(ruleId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }




}
