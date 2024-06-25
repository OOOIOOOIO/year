package com.sh.year.domain.goal.review.biggoalreview.application;

import com.sh.year.domain.goal.review.biggoalreview.api.dto.BigGoalReviewReqDto;
import com.sh.year.domain.goal.review.biggoalreview.domain.BigGoalReview;
import com.sh.year.domain.goal.review.biggoalreview.domain.repository.BigGoalReviewRepository;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BigGoalReviewService {

    private final BigGoalReviewRepository bigGoalReviewRepository;
    private final BigGoalRepository bigGoalRepository;
    /**
     * 조회,
     */


    /**
     * 큰목표 후기 저장
     */
    public void saveBigGoalReview(Long bigGoalId, BigGoalReviewReqDto bigGoalReviewReqDto){
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        BigGoalReview bigGoalReview = BigGoalReview.createBigGoalReview(bigGoalReviewReqDto);
        bigGoalReview.setBigGoal(bigGoal);

    }

    /**
     * 큰목표 후기 수정
     */
    public void updateBigGoalReview(Long reviewId, BigGoalReviewReqDto bigGoalReviewReqDto){
        BigGoalReview bigGoalReview = bigGoalReviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoalReview));

        bigGoalReview.updateBigGoalReview(bigGoalReviewReqDto);
    }

    /**
     * 큰목표 후기 삭제
     */
    public void deleteBigGoalReview(Long reviewId){
        bigGoalReviewRepository.deleteById(reviewId);

    }


}
