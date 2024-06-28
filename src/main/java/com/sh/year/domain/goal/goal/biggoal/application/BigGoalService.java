package com.sh.year.domain.goal.goal.biggoal.application;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.ShareStatus;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BigGoalService {

    private static final Integer DAILY_ALARM_STATE = 1;
    private static final Integer DAILY_REPEAT_SIZE = 7;
    private final BigGoalRepository bigGoalRepository;
    private final BigGoalQueryRepositoryImpl bigGoalQueryRepository;
    private final UsersRepository usersRepository;


    /**
     * 큰목표 기본 조회
     */
    @LogTrace
    public BigGoalResDto getBigGoalInfo(Long bigGoalId){

        BigGoal bigGoal = bigGoalQueryRepository.getBigGoalInfoByBigGoalId(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        List<SmallGoalResDto> smallGoalResDtoList = bigGoal.getSmallGoalList().stream()
                .map(SmallGoalResDto::new)
                .collect(Collectors.toList());

        BigGoalResDto bigGoalResDto = new BigGoalResDto(bigGoal);

        int progress = calculateBigGoalProgressByDto(smallGoalResDtoList);

        bigGoalResDto.setProgress(progress);
        bigGoalResDto.setSmallGoalResDtoList(smallGoalResDtoList);
        bigGoalResDto.setSmallGoalCnt(smallGoalResDtoList.size());

        return bigGoalResDto;
    }



    /**
     * 큰 목표 리스트 조회 페이징 처리
     *
     */
    @LogTrace
    public List<BigGoalMainResDto> getBigGoalPaging(UserInfoFromHeaderDto userInfoFromTokenDto,
                                                  Pageable pageable){
        Users users = getUsers(userInfoFromTokenDto);

        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsers(users, pageable);


        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();

        for(BigGoal bigGoal : bigGoalList){

            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);

            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

            int smallGoalSize = smallGoalList.size();

            bigGoalMainResDto.setSmallGoalCnt(smallGoalSize);

            int progress = calculateBigGoalProgressByEntity(smallGoalList);

            bigGoalMainResDto.setProgress(progress);

            bigGoalMainResDtoList.add(bigGoalMainResDto);

        }
        log.info("===========");
        log.info("===========");
        log.info("===========");
        log.info("===========");
        List<BigGoal> bigGoalList2 = bigGoalQueryRepository.getBigGoalListByUserId(userInfoFromTokenDto.getUserId());

        return bigGoalMainResDtoList;

    }


//    /**
//     * main에서 사용,
//     * 유저의 bigGoal들에 해댱하는 SmallGoal 가져오기
//     */
//    @LogTrace
//    public List<BigGoalMainResDto> getBigGoalListForMain(UserInfoFromHeaderDto userInfoFromHeaderDto){
////        List<BigGoal> bigGoalList = bigGoalQueryRepository.getBigGoalListByUserId(userInfoFromHeaderDto.getUserId());
//        Users users = getUsers(userInfoFromHeaderDto);
//        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsers(users);
//        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();
//
//
//        for(BigGoal bigGoal : bigGoalList){
//
//            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();
//
//            int progress = calculateBigGoalProgressByEntity(smallGoalList);
//
//            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);
//
//            bigGoalMainResDto.setProgress(progress);
//            bigGoalMainResDto.setSmallGoalCnt(smallGoalList.size());
//
//            bigGoalMainResDtoList.add(bigGoalMainResDto);
//        }
//
//        return bigGoalMainResDtoList;
//
//    }


    /**
     * main에서 사용,
     * 유저의 bigGoal들에 해댱하는 SmallGoal 가져오기
     */
    @LogTrace
    public List<BigGoal> getBigGoalListForMain(UserInfoFromHeaderDto userInfoFromHeaderDto){
//        List<BigGoal> bigGoalList = bigGoalQueryRepository.getBigGoalListByUserId(userInfoFromHeaderDto.getUserId());
        Users users = getUsers(userInfoFromHeaderDto);
        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsers(users);

        return bigGoalList;

    }

    /**
     * 큰목표 저장
     */
    @LogTrace
    public Long saveBigGoal(UserInfoFromHeaderDto userInfoFromHeaderDto, BigGoalReqDto bigGoalReqDto) {

        Users users = getUsers(userInfoFromHeaderDto);

        BigGoal bigGoal = BigGoal.createBigGoal(bigGoalReqDto, users);

        BigGoal savedBigGoal = bigGoalRepository.save(bigGoal);

        return savedBigGoal.getBigGoalId();
    }



    /**
     * 큰목표 수정
     */
    @LogTrace
    public void updateBigGoal(Long goalId, BigGoalReqDto bigGoalReqDto){

        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        // 공통 goal update
        bigGoal.updateBigGoal(bigGoalReqDto);

    }


    /**
     * 큰목표 삭제
     */
    @LogTrace
    public void deleteBigGoal(Long goalId){
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        bigGoalRepository.delete(bigGoal);

    }


    /**
     * 큰목표 공유여부 설정
     */
    @LogTrace
    public void updateBigGoalShareStatus(Long goalId){
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        ShareStatus shareStatus = bigGoal.getShareStatus();
        bigGoal.updateShareStatus(shareStatus);

    }

    /**
     * 큰목표 달성여부 설정
     */
    @LogTrace
    public void updateBigGoalCompleteStatus(Long goalId) {
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        CompleteStatus completeStatus = bigGoal.getCompleteStatus();
        bigGoal.updateCompleteStatus(completeStatus);

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
     * big goal progress 계산
     */
    private int calculateBigGoalProgressByDto(List<SmallGoalResDto> smallGoalResDtoList){
        int bigGoalProgress = 0;
        int size = smallGoalResDtoList.size();

        // smallGoal이 없을 경우
        if(size == 0) return 0;

        // small goal
        for(int i = 0; i < size; i++){
            int completeDayCnt = 0;
            int smallGoalProgress = 0;
            int totalDayCnt = 0;

            List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalResDtoList.get(i).getRuleResDto().getRuleCompleteInfoDtoList();

            // rule complete info
            for(int rci = 0; rci < ruleCompleteInfoDtoList.size(); rci++){
                byte[] completeDayArr = ruleCompleteInfoDtoList.get(rci).getCompleteDay();
                totalDayCnt = ruleCompleteInfoDtoList.get(rci).getTotalDayCnt();

                for(int day = 0; day < completeDayArr.length; day++){
                    if(completeDayArr[day] == 1) completeDayCnt++;
                }

            }

            if(totalDayCnt == 0){
                smallGoalProgress = 0;
            }
            else{
                smallGoalProgress = Math.round((completeDayCnt * 100) / totalDayCnt);
            }

            smallGoalResDtoList.get(i).setProgress(smallGoalProgress);

            bigGoalProgress += smallGoalProgress;
        }

        return Math.round(bigGoalProgress / size);
    }

    private int calculateBigGoalProgressByEntity(List<SmallGoal> smallGoalList){
        int bigGoalProgress = 0;
        int size = smallGoalList.size();

        // smallGoal이 없을 경우
        if(size == 0) return 0;

        // small goal
        for(int i = 0; i < size; i++){
            int completeDayCnt = 0;
            int smallGoalProgress = 0;
            int totalDayCnt = 0;

            List<RuleCompleteInfo> ruleCompleteInfoList = smallGoalList.get(i).getRule().getRuleCompleteInfoList();


            // rule complete info
            for(int rci = 0; rci < ruleCompleteInfoList.size(); rci++){
                byte[] completeDayArr = ruleCompleteInfoList.get(rci).getCompleteDay();
                totalDayCnt = ruleCompleteInfoList.get(rci).getTotalDayCnt();

                for(int day = 0; day < completeDayArr.length; day++){
                    if(completeDayArr[day] == 1) completeDayCnt++;
                }

            }
            if(totalDayCnt == 0){
                smallGoalProgress = 0;
            }
            else{
                smallGoalProgress = Math.round((completeDayCnt * 100) / totalDayCnt);
            }

            bigGoalProgress += smallGoalProgress;
        }

        return Math.round(bigGoalProgress / size);
    }





}
