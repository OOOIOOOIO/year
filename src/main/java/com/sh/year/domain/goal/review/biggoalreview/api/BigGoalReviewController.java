package com.sh.year.domain.goal.review.biggoalreview.api;


import com.sh.year.domain.goal.review.biggoalreview.api.dto.BigGoalReviewReqDto;
import com.sh.year.domain.goal.review.biggoalreview.application.BigGoalReviewService;
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

@Tag(name = "Big Goal Review", description = "후기 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/biggoal/review")
public class BigGoalReviewController {

    private final BigGoalReviewService bigGoalReviewService;

    /**
     * 조회,
     */


    /**
     * 큰목표 후기 저장
     */
    @Operation(
            summary = "큰목표 후기 저장 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 후기 저장에 성공하였습니다."
    )
    @PostMapping("/{bigGoalId}")
    public ResponseEntity<String> saveBigGoalReview(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
                                  @PathVariable("bigGoalId") Long bigGoalId,
                                  @RequestBody BigGoalReviewReqDto bigGoalReviewReqDto){

        bigGoalReviewService.saveBigGoalReview(bigGoalId, bigGoalReviewReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 큰목표 후기 수정
     */
    @Operation(
            summary = "큰목표 후기 수정 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 후기 수정에 성공하였습니다."
    )
    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateBigGoalReview(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
                                                      @PathVariable("reviewId") Long reviewId,
                                                      @RequestBody BigGoalReviewReqDto bigGoalReviewReqDto){

        bigGoalReviewService.updateBigGoalReview(reviewId, bigGoalReviewReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * 큰목표 후기 삭제
     */
    @Operation(
            summary = "큰목표 후기 삭제 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "큰목표 후기 삭제에 성공하였습니다."
    )
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteBigGoalReview(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
                                    @PathVariable("reviewId") Long reviewId){

        bigGoalReviewService.deleteBigGoalReview(reviewId);

        return new ResponseEntity<>("success", HttpStatus.OK);

    }


}
