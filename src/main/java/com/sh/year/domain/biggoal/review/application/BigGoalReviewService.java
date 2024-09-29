package com.sh.year.domain.biggoal.review.application;

import com.sh.year.domain.biggoal.review.domain.BigGoalReview;
import com.sh.year.domain.biggoal.review.api.dto.req.BigGoalReviewReqDto;
import com.sh.year.domain.biggoal.review.domain.repository.BigGoalReviewRepository;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.biggoal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
     * 큰목표 후기 저장
     */
    public void saveBigGoalReview(Long bigGoalId, BigGoalReviewReqDto bigGoalReviewReqDto){
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        bigGoal.updateCompleteStatus(CompleteStatus.COMP);

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
