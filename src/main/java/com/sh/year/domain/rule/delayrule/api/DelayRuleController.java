package com.sh.year.domain.rule.delayrule.api;

import com.sh.year.api.main.controller.dto.res.DelayGoalListResDto;
import com.sh.year.api.main.controller.dto.res.DelayRuleResDto;
import com.sh.year.domain.smallgoal.review.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.rule.delayrule.application.DelayRuleService;
import com.sh.year.domain.smallgoal.smallgoal.api.dto.res.SmallGoalResDto;
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

@Tag(name = "Delay Rule", description = "연기된 목표 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rule/delayrule")
public class DelayRuleController {

    private final DelayRuleService delayRuleService;
    /**
     * 연기된 목표 조회장
     */
    @Operation(
            summary = "연기된 목표 상세 조회 API",
            description = "연기된 목표 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "연기된 목표 상세 조회에 성공했습니다."
    )
    @LogTrace
    @GetMapping("/{delayRuleId}")
    public ResponseEntity<SmallGoalResDto> getDelayGoalInfo(@PathVariable(value = "delayRuleId") Long delayRuleId){
        SmallGoalResDto delayRuleInfo = delayRuleService.getDelayRuleInfo(delayRuleId);

        return new ResponseEntity<>(delayRuleInfo, HttpStatus.OK);

    }


    /**
     * 연기된 규칙 리스트 조회
     */
    @Operation(
            summary = "연기된 목표 리스트 조회(Delay, Fail) API",
            description = "연기된 목표 리스트 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "연기된 목표 리스트 조회에 성공했습니다."
    )
    @LogTrace
    @GetMapping("/list")
    public ResponseEntity<DelayGoalListResDto> getDelayGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){
        List<DelayRuleResDto> delayRuleList = delayRuleService.getDelayruleList(userInfoFromTokenDto);

        return new ResponseEntity<>(new DelayGoalListResDto(delayRuleList), HttpStatus.OK);
    }


//    /**
//     * 연기된 목표 생성 및 저장
//     */
//    @Operation(
//            summary = "Delay Rule 저장",
//            description = "Delay Rule 저장"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "Delay Rule 저장에 성공했습니다."
//    )
//    @LogTrace
//    @PostMapping("/{ruleId}")
//    public ResponseEntity<String> saveDelayGoal(@PathVariable(value = "ruleId") Long ruleId,
//                                                @UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){
//        delayRuleService.saveDelayGoal(userInfoFromTokenDto, ruleId);
//
//        return new ResponseEntity<>("suceess", HttpStatus.OK);
//    }


//    /**
//     * 연기된 목표 삭제
//     */
//    @Operation(
//            summary = "Delay Goal API",
//            description = "Delay Goal 삭제"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "Delay Goal 삭제에 성공했습니다."
//    )
//    @LogTrace
//    @DeleteMapping("/{delayGoalId}")
//    public void deleteDelayGoal(@PathVariable(value = "delayGoalId") Long delayGoalId){
//        delayGoalService.deleteDelayGoal(delayGoalId);
//
//    }

    /**
     * 연기된 목표 리뷰 저장
     */
    @Operation(
            summary = "연기된 목표 리뷰 저장 및 성공 처리 API",
            description = "연기된 목표 리뷰 저장 및 성공 처리"
    )
    @ApiResponse(
            responseCode = "200",
            description = "연기된 목표 리뷰 저장 및 성공 처리에 성공했습니다."
    )
    @LogTrace
    @PostMapping("/review/{delayGoalId}")
    public void updateFailStatus(@PathVariable(value = "delayGoalId") Long delayGoalId,
                            @RequestBody SmallGoalReviewReqDto smallGoalReviewReqDto){

//        delayRuleService.setSuccess(delayGoalId);
        delayRuleService.saveDelayRuleReview(delayGoalId, smallGoalReviewReqDto);
    }


}
