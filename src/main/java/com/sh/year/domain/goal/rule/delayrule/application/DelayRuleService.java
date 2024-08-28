package com.sh.year.domain.goal.rule.delayrule.application;

import com.sh.year.api.main.controller.dto.res.DelayRuleResDto;
import com.sh.year.domain.goal.review.smallgoalreview.api.dto.req.SmallGoalReviewReqDto;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.review.smallgoalreview.domain.repository.SmallGoalReviewRepository;
import com.sh.year.domain.goal.rule.delayrule.domain.DelayRule;
import com.sh.year.domain.goal.rule.delayrule.domain.repository.DelayRuleRepository;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleQueryRepositoryImpl;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import com.sh.year.domain.goal.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DelayRuleService {

    private final DelayRuleRepository delayRuleRepository;
    private final UsersRepository usersRepository;
    private final RuleRepository ruleRepository;
    private final RuleQueryRepositoryImpl ruleQueryRepository;
    private final SmallGoalReviewRepository smallGoalReviewRepository;


    /**
     * 연기된 목표 상세 조회
     */
    public SmallGoalResDto getDelayRuleInfo(Long delayGoalId){
        DelayRule delayRule = delayRuleRepository.findById(delayGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistDelayGoal));

        Rule rule = delayRule.getRule(); // rule 1:1

        SmallGoal smallGoal = rule.getSmallGoal();// smallGoal 1:1

        SmallGoalResDto smallGoalResDto = new SmallGoalResDto(smallGoal);

        List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalResDto.getRuleResDto().getRuleCompleteInfoDtoList();

        float progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

        smallGoalResDto.setProgress(progress);

        return smallGoalResDto;

    }


    /**
     * 연기된 목표 리스트 조회, delay & fail
     */
    public List<DelayRuleResDto> getDelayruleList(UserInfoFromHeaderDto userInfoFromHeaderDto){
        List<DelayRule> delayRuleList = delayRuleRepository.findAllByUsersAndCompleteStatus(userInfoFromHeaderDto.getUserId());

        List<DelayRuleResDto> delayRuleResDtoList = new ArrayList<>();

        for (DelayRule delayRule : delayRuleList) {
            Rule rule = delayRule.getRule(); // rule 1:1

            SmallGoal smallGoal = rule.getSmallGoal();// smallGoal 1:1

            DelayRuleResDto delayRuleResDto = new DelayRuleResDto(smallGoal, rule, delayRule.getCompleteStatus(), delayRule.getDelayRuleId(), delayRule.getEndDate());

            List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = delayRuleResDto.getRuleResDto().getRuleCompleteInfoDtoList();

            float progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

            delayRuleResDto.setProgress(progress);

            int completeStatus = checkRuleCompleteStatus(delayRuleResDto.getRuleResDto().getRuleCompleteInfoDtoList(), delayRule.getCreatedAt().toLocalDate().minusDays(1));
            delayRuleResDto.getRuleResDto().setCompleteStatus(completeStatus);

            delayRuleResDtoList.add(delayRuleResDto);
        }

        return delayRuleResDtoList;
    }

    /**
     * 연기 목표 저장
     */
    public void saveDelayGoal(UserInfoFromHeaderDto userInfoFromTokenDto, Long ruleId) {

        Rule rule = ruleRepository.findById(ruleId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        Users users = getUsers(userInfoFromTokenDto);

        DelayRule delayRule = DelayRule.createDelayGoal(rule, users);

        delayRuleRepository.save(delayRule);

    }

    /**
     * 연기된 목표 삭제
     */
    public void deleteDelayGoal(Long delayGoalId){
        delayRuleRepository.deleteById(delayGoalId);

    }


    /**
     * 연기된 목표 성공 처리
     */
    public void setSuccess(Long delayGoalId){
        DelayRule delayRule = delayRuleRepository.findById(delayGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistDelayGoal));
        LocalDateTime createdAt = delayRule.getCreatedAt().minusDays(1); // 하루전
        int targetDay = createdAt.getDayOfMonth();

        Rule rule = delayRule.getRule(); // rule 1:1

        Rule targetRule = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(createdAt.getYear(), createdAt.getMonth().getValue(), rule.getRuleId()).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        byte[] completeDay = targetRule.getRuleCompleteInfoList().get(0).getCompleteDay();

        completeDay[targetDay] = 1;

        // rci update
        targetRule.getRuleCompleteInfoList().get(0).updateCompleteDayArr(completeDay);

        // delayGoal completeStatus update
        delayRule.updateCompleteStatusToComplete();


    }


    /**
     * ======================================================================================================================================================
     * ======================================================================================================================================================
     *
     */

    private Users getUsers(UserInfoFromHeaderDto userInfoFromHeaderDto) {

        return usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }



    /**
     * small goal progress 계산
     */
    private float calculateProgress(List<RuleCompleteInfoDto> ruleCompleteInfoDtoList, int totalDayCnt){
        int cnt = 0;

        if(totalDayCnt == 0) return 0;

        for(int i = 0; i < ruleCompleteInfoDtoList.size(); i++){
            byte[] completeDayArr = ruleCompleteInfoDtoList.get(i).getCompleteDay();

            for(int day = 0; day < completeDayArr.length; day++){
                if(completeDayArr[day] == 1) cnt++;
            }
        }

        return ((float)cnt / (float)totalDayCnt) * 100;
    }


    /**
     * rule completeStatus 계산
     */
    private int checkRuleCompleteStatus(List<RuleCompleteInfoDto> ruleCompleteInfoDtoList, LocalDate targetDay){
        int year = targetDay.getYear();
        int month = targetDay.getMonthValue();
        int day = targetDay.getDayOfMonth();

        for(int i = 0; i < ruleCompleteInfoDtoList.size(); i++){
            RuleCompleteInfoDto ruleCompleteInfoDto = ruleCompleteInfoDtoList.get(i);
            int rciYear = ruleCompleteInfoDto.getYear();
            int rciMonth= ruleCompleteInfoDto.getMonth();

            if(year == rciYear && month == rciMonth){
                byte[] completeDayArr = ruleCompleteInfoDto.getCompleteDay();
                if(completeDayArr[day] == 1){
                    return 1;
                }
            }


        }

        return 0;
    }


    /**
     * delay rule reivew 저장
     */
    public void saveDelayRuleReview(Long delayGoalId, SmallGoalReviewReqDto smallGoalReviewReqDto) {
        DelayRule delayRule = delayRuleRepository.findById(delayGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistDelayGoal));
        LocalDateTime createdAt = delayRule.getCreatedAt().minusDays(1); // 하루전
        int targetDay = createdAt.getDayOfMonth();

        Rule rule = delayRule.getRule(); // rule 1:1

        // review save
        SmallGoalReview smallGoalReview = SmallGoalReview.createSmallGoalReview(smallGoalReviewReqDto);
        smallGoalReview.setRule(rule);

        smallGoalReviewRepository.save(smallGoalReview);

        Rule targetRule = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(createdAt.getYear(), createdAt.getMonth().getValue(), rule.getRuleId()).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        byte[] completeDay = targetRule.getRuleCompleteInfoList().get(0).getCompleteDay();

        completeDay[targetDay] = 1;

        // rci update
        targetRule.getRuleCompleteInfoList().get(0).updateCompleteDayArr(completeDay);

        // delayGoal completeStatus update
        delayRule.updateCompleteStatusToComplete();


    }


}
