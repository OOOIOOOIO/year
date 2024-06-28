package com.sh.year.domain.goal.review.smallgoalreview.application;

import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.res.SmallGoalReviewResDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.res.SmallGoalReviewResListDto;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.review.smallgoalreview.domain.repository.SmallGoalReviewRepository;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmallGoalReviewService {

    private final SmallGoalReviewRepository smallGoalReviewRepository;
    private final SmallGoalRepository smallGoalRepository;


    /**
     * 작은 후기 리스트로 조회
     *
     */
    public SmallGoalReviewResListDto getSmallGoalReview(Long smallGoalId, Pageable pageable) {
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        List<SmallGoalReviewResDto> smallGoalReviewResDtos = smallGoalReviewRepository.findAllBySmallGaol(smallGoal, pageable).stream()
                .map(SmallGoalReviewResDto::new)
                .collect(Collectors.toList());


        return new SmallGoalReviewResListDto(smallGoalReviewResDtos);

    }


    /**
     * 작은목표 후기 저장
     */
    public void saveSmallGoalReview(Long smallGoalId, SmallGoalReviewReqDto smallGoalReviewReqDto){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        SmallGoalReview smallGoalReview = SmallGoalReview.createSmallGoalReview(smallGoalReviewReqDto);
        smallGoalReview.setSmallGoal(smallGoal);

        smallGoalReviewRepository.save(smallGoalReview);
    }

//    /**
//     * 작은목표 후기 수정
//     */
//    public void updateSmallGoalReview(Long reviewId, SmallGoalReviewReqDto smallGoalReviewReqDto){
//        SmallGoalReview smallGoalReview = smallGoalReviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoalReview));
//
//        smallGoalReview.updateSmallGoalReview(smallGoalReviewReqDto);
//    }
//
//    /**
//     * 큰목표 후기 삭제
//     */
//    public void deleteSmallGoalReview(Long reviewId){
//        smallGoalReviewRepository.deleteById(reviewId);
//
//    }


}
