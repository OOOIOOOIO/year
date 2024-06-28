package com.sh.year.domain.goal.goal.biggoal.api;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalListResDto;
import com.sh.year.domain.goal.goal.biggoal.application.BigGoalService;
import com.sh.year.global.common.ResponseConst;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @LogTrace
    @GetMapping("/{bigGoalId}")
    public ResponseEntity<BigGoalResDto> getBigGoalInfo(@PathVariable(value = "bigGoalId") Long bigGoalId){

        BigGoalResDto bigGoalInfo = bigGoalService.getBigGoalInfo(bigGoalId);

        return new ResponseEntity<>(bigGoalInfo, HttpStatus.OK);
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
    @LogTrace
    @PutMapping("/{bigGoalId}")
    public ResponseEntity<String> updateBigGoal(@PathVariable(value = "bigGoalId") Long bigGoalId,
                                                @RequestBody BigGoalReqDto bigGoalReqDto){

        bigGoalService.updateBigGoal(bigGoalId, bigGoalReqDto);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }


    /**
     * 내 큰목표들 보기 페이징처리
     */
    @Operation(
            summary = "큰목표 리스트로 조회 API",
            description = "큰목표, 페이징처리"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 리스트로 조회에 성공하였습니다."
    )
    @LogTrace
    @GetMapping("/list")
    public ResponseEntity<BigGoalListResDto> getBigGoalList(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
                                                            @PageableDefault(size = 5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        List<BigGoalMainResDto> bigGoalList = bigGoalService.getBigGoalPaging(userInfoFromTokenDto, pageable);

        return new ResponseEntity<>(new BigGoalListResDto(bigGoalList), HttpStatus.OK);
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
    @LogTrace
    @PostMapping("")
    public ResponseEntity<Long> saveBigGoal(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto,
                                           @RequestBody BigGoalReqDto bigGoalReqDto){

        Long goalId = bigGoalService.saveBigGoal(userInfoFromHeaderDto, bigGoalReqDto);

        return new ResponseEntity<>(goalId, HttpStatus.OK);

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
    @LogTrace
    @DeleteMapping("/{bigGoalId}")
    public ResponseEntity<String> deleteBigGoal(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.deleteBigGoal(bigGoalId);

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
    @LogTrace
    @PutMapping("/share/{bigGoalId}")
    public ResponseEntity<String> updateBigGoalShareStatus(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.updateBigGoalShareStatus(bigGoalId);

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
    @LogTrace
    @PutMapping("/comp/{bigGoalId}")
    public ResponseEntity<String> updateBigGoalCompleteStatus(@PathVariable(value = "bigGoalId") Long bigGoalId){

        bigGoalService.updateBigGoalCompleteStatus(bigGoalId);

        return new ResponseEntity<>(ResponseConst.SUCCESS.value(), HttpStatus.OK);
    }





}
