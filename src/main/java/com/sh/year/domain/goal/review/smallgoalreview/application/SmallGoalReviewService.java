package com.sh.year.domain.goal.review.smallgoalreview.application;

import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.review.smallgoalreview.domain.repository.SmallGoalReviewRepository;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmallGoalReviewService {

    private final SmallGoalReviewRepository smallGoalReviewRepository;
    private final SmallGoalRepository smallGoalRepository;


    /**
     * 큰목표 후기 리스트로 조회
     *
     * 날짜순(createdAt)으로 ASC 정렬
     * page=1&size=5
     *  @PagingDefault
     * 5개씩
     *
     * 파라미터로 page 받기 0부터 시작
     */
    public void getSmallGoalReview(Long smallGoalId) {

//        smallGoalReviewRepository.findAll(Sort.Direction.ASC, "createdAt")

    }


    /**
     * 큰목표 후기 저장
     */
    public void saveSmallGoalReview(Long smallGoalId, SmallGoalReviewReqDto smallGoalReviewReqDto){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        SmallGoalReview smallGoalReview = SmallGoalReview.createSmallGoalReview(smallGoalReviewReqDto);
        smallGoalReview.setSmallGoal(smallGoal);
    }

    /**
     * 큰목표 후기 수정
     */
    public void updateSmallGoalReview(Long reviewId, SmallGoalReviewReqDto smallGoalReviewReqDto){
        SmallGoalReview smallGoalReview = smallGoalReviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoalReview));

        smallGoalReview.updateSmallGoalReview(smallGoalReviewReqDto);
    }

    /**
     * 큰목표 후기 삭제
     */
    public void deleteSmallGoalReview(Long reviewId){
        smallGoalReviewRepository.deleteById(reviewId);

    }


}
