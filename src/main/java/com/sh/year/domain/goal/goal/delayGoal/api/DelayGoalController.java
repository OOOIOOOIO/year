package com.sh.year.domain.goal.goal.delayGoal.api;

import com.sh.year.api.main.controller.dto.res.DelayGoalListResDto;
import com.sh.year.api.main.controller.dto.res.DelayGoalResDto;
import com.sh.year.domain.goal.goal.delayGoal.application.DelayGoalService;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
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

import java.util.List;

@Tag(name = "Delay Goal", description = "Delay Goal API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goal/delaygoal")
public class DelayGoalController {

    private final DelayGoalService delayGoalService;
    /**
     * 연기된 목표 조회
     */
    @Operation(
            summary = "Delay Goal API",
            description = "Delay Goal 상세 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delay Goal 상세 조회에 성공했습니다."
    )
    @GetMapping("/{delayGoalId}")
    public ResponseEntity<SmallGoalResDto> getDelayGoalInfo(@PathVariable(value = "delayGoalId") Long delayGoalId){
        SmallGoalResDto delayGoalInfo = delayGoalService.getDelayGoalInfo(delayGoalId);

        return new ResponseEntity<>(delayGoalInfo, HttpStatus.OK);

    }


    /**
     * 연기된 목표 리스트 조회
     */
    @Operation(
            summary = "Delay Goal API",
            description = "Delay Goal 리스트 조회"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delay Goal 리스트 조회에 성공했습니다."
    )
    @GetMapping("/list")
    public ResponseEntity<DelayGoalListResDto> getDelayGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){
        List<DelayGoalResDto> delayGoalList = delayGoalService.getDelayGoalList(userInfoFromTokenDto);

        return new ResponseEntity<>(new DelayGoalListResDto(delayGoalList), HttpStatus.OK);
    }

    /**
     * 연기된 목표 저장
     */
    @Operation(
            summary = "Delay Goal API",
            description = "Delay Goal 저장"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delay Goal 저장에 성공했습니다."
    )
    @PostMapping("/ruleId")
    public ResponseEntity<String> saveDelayGoal(@PathVariable(value = "ruleId") Long ruleId,
                                                @UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto){
        delayGoalService.saveDelayGoal(userInfoFromTokenDto, ruleId);

        return new ResponseEntity<>("suceess", HttpStatus.OK);
    }


    /**
     * 연기된 목표 삭제
     */
    @Operation(
            summary = "Delay Goal API",
            description = "Delay Goal 삭제"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delay Goal 삭제에 성공했습니다."
    )
    @DeleteMapping("/{delayGoalId}")
    public void deleteDelayGoal(@PathVariable(value = "delayGoalId") Long delayGoalId){
        delayGoalService.deleteDelayGoal(delayGoalId);

    }

    /**
     * 연기된 목표 성공 처리
     */
    @Operation(
            summary = "Delay Goal API",
            description = "Delay Goal 성공 처리"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Delay Goal 성공 처리에 성공했습니다."
    )
    @PatchMapping("/{delayGoalId}")
    public void updateFailStatus(@PathVariable(value = "delayGoalId") Long delayGoalId){

        delayGoalService.setSuceess(delayGoalId);
    }


}
