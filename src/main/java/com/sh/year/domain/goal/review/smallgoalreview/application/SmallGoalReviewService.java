package com.sh.year.domain.goal.review.smallgoalreview.application;

import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.res.SmallGoalReviewResDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.res.SmallGoalReviewResListDto;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.review.smallgoalreview.domain.repository.SmallGoalReviewRepository;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleQueryRepositoryImpl;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmallGoalReviewService {

    private final SmallGoalReviewRepository smallGoalReviewRepository;
    private final SmallGoalRepository smallGoalRepository;
    private final RuleRepository ruleRepository;
    private final RuleQueryRepositoryImpl ruleQueryRepository;


    /**
     * 작은 후기 리스트로 조회
     *
     */
    @LogTrace
    public SmallGoalReviewResListDto getSmallGoalReview(Long ruleId, Pageable pageable) {
        Rule rule = ruleRepository.findById(ruleId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        List<SmallGoalReview> smallGoalReviewList = smallGoalReviewRepository.findAllByRule(rule, pageable);

        List<SmallGoalReviewResDto> smallGoalReviewResDtos = smallGoalReviewList.stream()
                .map(SmallGoalReviewResDto::new)
                .collect(Collectors.toList());


        return new SmallGoalReviewResListDto(smallGoalReviewResDtos);

    }



    /**
     * 작은목표 후기 저장(멀쩡한 애)
     */
    @LogTrace
    public void saveSmallGoalReview(Long ruleId, SmallGoalReviewReqDto smallGoalReviewReqDto){

        LocalDate now = LocalDate.now();

        Rule rule = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(now.getYear(), now.getMonth().getValue(), ruleId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        // review save
        SmallGoalReview smallGoalReview = SmallGoalReview.createSmallGoalReview(smallGoalReviewReqDto);
        smallGoalReview.setRule(rule);

        smallGoalReviewRepository.save(smallGoalReview);

        // rci update
        int today = now.getDayOfMonth();
        byte[] completeDayArr = rule.getRuleCompleteInfoList().get(0).getCompleteDay();

        completeDayArr[today] = 1;

        rule.getRuleCompleteInfoList().get(0).updateCompleteDayArr(completeDayArr);


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
