package com.sh.year.domain.goal.review.smallgoalreview.api;


import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.res.SmallGoalReviewResListDto;
import com.sh.year.domain.goal.review.smallgoalreview.application.SmallGoalReviewService;
import com.sh.year.global.log.LogTrace;
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

@Tag(name = "Small Goal Review", description = "후기 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/smallgoal/review")
public class SmallGoalReviewController {

    private final SmallGoalReviewService smallGoalReviewService;


    /**
     * 직은 목표 후기 리스트 조회
     */
    @Operation(
            summary = "작은목표 후기 리스트 조회 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 후기 리스트 조회에 성공하였습니다."
    )
    @GetMapping("/{ruleId}")
    @LogTrace
    public ResponseEntity<SmallGoalReviewResListDto> getSmallGoalReviewList(@PathVariable("ruleId") Long ruleId,
                                                      @PageableDefault(size = 5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        SmallGoalReviewResListDto smallGoalReviewList = smallGoalReviewService.getSmallGoalReview(ruleId, pageable);

        return new ResponseEntity<>(smallGoalReviewList, HttpStatus.OK);
    }



    /**
     * 작은목표 후기 저장
     */
    @Operation(
            summary = "작은목표 후기 저장 API",
            description = "큰목표"
    )
    @ApiResponse(
            responseCode = "200",
            description = "작은목표 후기 저장에 성공하였습니다."
    )
    @PostMapping("/{ruleId}")
    @LogTrace
    public ResponseEntity<String> saveSmallGoalReview(@PathVariable("ruleId") Long ruleId,
                                        @RequestBody SmallGoalReviewReqDto smallGoalReviewReqDto){

        smallGoalReviewService.saveSmallGoalReview(ruleId, smallGoalReviewReqDto);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

//    /**
//     * 큰목표 후기 수정
//     */
//    @Operation(
//            summary = "작은목표 후기 수정 API",
//            description = "큰목표"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "작은목표 후기 수정에 성공하였습니다."
//    )
//    @PutMapping("/{reviewId}")
//    public ResponseEntity<String> updateSmallGoalReview(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
//                                      @PathVariable("reviewId") Long reviewId,
//                                      @RequestBody SmallGoalReviewReqDto smallGoalReviewReqDto){
//
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }

//    /**
//     * 큰목표 후기 삭제
//     */
//    @Operation(
//            summary = "작은목표 후기 삭제 API",
//            description = "큰목표"
//    )
//    @ApiResponse(
//            responseCode = "200",
//            description = "작은목표 후기 삭제에 성공하였습니다."
//    )
//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<String> deleteSmallGoalReview(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromTokenDto,
//                                      @PathVariable("reviewId") Long reviewId){
//
//        return new ResponseEntity<>("success", HttpStatus.OK);
//    }


}
