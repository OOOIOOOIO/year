package com.sh.year.domain.biggoal.biggoal.application;

import com.sh.year.domain.biggoal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.domain.biggoal.biggoal.api.dto.res.*;
import com.sh.year.domain.biggoal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.biggoal.biggoal.domain.ShareStatus;
import com.sh.year.domain.biggoal.biggoal.domain.repository.BigGoalQueryRepositoryImpl;
import com.sh.year.domain.biggoal.review.api.dto.res.BigGoalReviewResDto;
import com.sh.year.domain.biggoal.review.domain.BigGoalReview;
import com.sh.year.domain.biggoal.review.domain.repository.BigGoalReviewRepository;
import com.sh.year.domain.common.CompleteStatus;
import com.sh.year.domain.smallgoal.smallgoal.api.dto.res.SmallGoalResDto;
import com.sh.year.domain.smallgoal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.log.LogTrace;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BigGoalService {

    private final BigGoalRepository bigGoalRepository;
    private final BigGoalQueryRepositoryImpl bigGoalQueryRepository;
    private final UsersRepository usersRepository;
    private final BigGoalReviewRepository bigGoalReviewRepository;


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

        float progress = calculateBigGoalProgressByDto(smallGoalResDtoList);

        bigGoalResDto.setProgress(progress);
        bigGoalResDto.setSmallGoalResDtoList(smallGoalResDtoList);
        bigGoalResDto.setSmallGoalCnt(smallGoalResDtoList.size());

        return bigGoalResDto;
    }



    /**
     * 큰 목표 리스트 조회 페이징 처리
     *
     * 성취한 목표 제외
     *
     */
    @LogTrace
    public List<BigGoalMainResDto> getMainViewAPi(UserInfoFromHeaderDto userInfoFromTokenDto,
                                                  Pageable pageable){
        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsersAndCompleteStatusNotComp(userInfoFromTokenDto.getUserId(), pageable);

        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();

        for(BigGoal bigGoal : bigGoalList){

            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);

            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

            int smallGoalSize = smallGoalList.size();

            bigGoalMainResDto.setSmallGoalCnt(smallGoalSize);

            float progress = calculateBigGoalProgressByEntity(smallGoalList);

            bigGoalMainResDto.setProgress(progress);

            bigGoalMainResDtoList.add(bigGoalMainResDto);

        }

        return bigGoalMainResDtoList;

    }

    /**
     * 큰 목표 리스트 조회 페이징 처리
     *
     */
    @LogTrace
    public List<BigGoalMainResDto> getBigGoalList(UserInfoFromHeaderDto userInfoFromTokenDto,
                                                  Pageable pageable){
        Users users = getUsers(userInfoFromTokenDto);

        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsers(users, pageable);

        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();

        for(BigGoal bigGoal : bigGoalList){

            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);

            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

            int smallGoalSize = smallGoalList.size();

            bigGoalMainResDto.setSmallGoalCnt(smallGoalSize);

            float progress = calculateBigGoalProgressByEntity(smallGoalList);

            bigGoalMainResDto.setProgress(progress);

            bigGoalMainResDtoList.add(bigGoalMainResDto);

        }

        return bigGoalMainResDtoList;

    }


    /**
     * 금년 성공한 큰목표들 조회 -WY028
     * 성공한 목표들의 개수
     * 성공한 목표들의 정보
     */
    @LogTrace
    public BigGoalCompleteListResDto getCompleteBigGoalList(UserInfoFromHeaderDto userInfoFromTokenDto){

        Users users = getUsers(userInfoFromTokenDto);

        List<BigGoal> bigGoalList = bigGoalRepository.findAllCompleteBigGoalByUserId(users.getUserId());

        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();

        for(BigGoal bigGoal : bigGoalList){

            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);

            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

            int smallGoalSize = smallGoalList.size();

            bigGoalMainResDto.setSmallGoalCnt(smallGoalSize);

            float progress = calculateBigGoalProgressByEntity(smallGoalList);

            bigGoalMainResDto.setProgress(progress);

            bigGoalMainResDtoList.add(bigGoalMainResDto);

        }

        BigGoalCompleteListResDto bigGoalCompleteListResDto = new BigGoalCompleteListResDto(bigGoalList.size(), bigGoalMainResDtoList);

        return bigGoalCompleteListResDto;
    }


    /**
     * 연도별 성취 결과(페이징처리) - WY029
     *
     * 연도
     * 연도별 성취 퍼센트
     * 금년 성공한 큰목표 개수
     *
     */
    @LogTrace
    public BigGoalCompleteYearAndProgressListResDto getEntireCompleteInfo(UserInfoFromHeaderDto userInfoFromTokenDto){
        Users users = getUsers(userInfoFromTokenDto);
        int joinYear = users.getCreatedAt().getYear();
        int currentYear = LocalDate.now().getYear();

        List<BigGoalCompleteYearAndProgressResDto> bigGoalCompleteYearAndProgressResDtoList = new ArrayList<>();
        int currentYearCompleteBigGoalCnt = 0;
        //가입 ~ 올해까지
        for(int year = joinYear; year <= currentYear; year++){

            LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0, 1);
            LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59, 0);

            List<BigGoal> bigGoalList = bigGoalRepository.findAllCompleteBigGoalByUserIdAndYear(users.getUserId(), startDate, endDate);

            if(currentYear == year) currentYearCompleteBigGoalCnt = bigGoalList.size();

            float totalProgress = 0f; // 해당 연도 전체의 progress
            BigGoalCompleteYearAndProgressResDto bigGoalCompleteYearAndProgressResDto = new BigGoalCompleteYearAndProgressResDto(year);

            for(BigGoal bigGoal : bigGoalList){

                List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

                float progress = calculateBigGoalProgressByEntity(smallGoalList);

                totalProgress += progress;

            }

            bigGoalCompleteYearAndProgressResDto.setProgress(totalProgress / bigGoalList.size());

            bigGoalCompleteYearAndProgressResDtoList.add(bigGoalCompleteYearAndProgressResDto);

        }




        BigGoalCompleteYearAndProgressListResDto bigGoalCompleteYearAndProgressListResDto = new BigGoalCompleteYearAndProgressListResDto(bigGoalCompleteYearAndProgressResDtoList, currentYearCompleteBigGoalCnt);

        return bigGoalCompleteYearAndProgressListResDto;
    }

    /**
     * 해당 연도의 성공한 큰목표들 조회(페이징처리) - WY030-1, -2
     * 성공개수/전체개수 -> 연도별로 리스트
     */
    @LogTrace
    public List<BigGoalMainResDto> getCompleteBigGoalListByYear(int year, UserInfoFromHeaderDto userInfoFromTokenDto){
        Users users = getUsers(userInfoFromTokenDto);

        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0, 1);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59, 0);

        List<BigGoal> bigGoalList = bigGoalRepository.findAllCompleteBigGoalByUserIdAndYear(users.getUserId(), startDate, endDate);

        List<BigGoalMainResDto> bigGoalMainResDtoList = new ArrayList<>();

        for(BigGoal bigGoal : bigGoalList){

            BigGoalMainResDto bigGoalMainResDto = new BigGoalMainResDto(bigGoal);

            List<SmallGoal> smallGoalList = bigGoal.getSmallGoalList();

            int smallGoalSize = smallGoalList.size();

            bigGoalMainResDto.setSmallGoalCnt(smallGoalSize);

            float progress = calculateBigGoalProgressByEntity(smallGoalList); // bigGoal 별 progress

            bigGoalMainResDto.setProgress(progress);

            bigGoalMainResDtoList.add(bigGoalMainResDto);

        }

        return bigGoalMainResDtoList;

    }



    /**
     * 성공한 큰목표 상세 조회 - WY031
     */
    @LogTrace
    public BigGoalCompleteInfoResDto getCompleteBigGoalInfo(Long bigGoalId){

        BigGoal bigGoal = bigGoalQueryRepository.getBigGoalInfoByBigGoalId(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        List<SmallGoalResDto> smallGoalResDtoList = bigGoal.getSmallGoalList().stream()
                .map(SmallGoalResDto::new)
                .collect(Collectors.toList());

        BigGoalCompleteInfoResDto bigGoalCompleteInfoResDto = new BigGoalCompleteInfoResDto(bigGoal);

        try {
            BigGoalReview bigGoalReview = bigGoalReviewRepository.findByBigGoal_BigGoalId(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistGoalReview));

            bigGoalCompleteInfoResDto.setBigGoalReviewResDto(new BigGoalReviewResDto(bigGoalReview));
        } catch (CustomException customException) {
            bigGoalCompleteInfoResDto.setBigGoalReviewResDto(null);
        }

        float progress = calculateBigGoalProgressByDto(smallGoalResDtoList);

        bigGoalCompleteInfoResDto.setProgress(progress);
        bigGoalCompleteInfoResDto.setSmallGoalResDtoList(smallGoalResDtoList);
        bigGoalCompleteInfoResDto.setSmallGoalCnt(smallGoalResDtoList.size());

        return bigGoalCompleteInfoResDto;
    }






    /**
     * main에서 사용,
     * 유저의 bigGoal들에 해댱하는 SmallGoal 가져오기
     */
    @LogTrace
    public List<BigGoal> getBigGoalListForMain(UserInfoFromHeaderDto userInfoFromHeaderDto){
//        List<BigGoal> bigGoalList = bigGoalQueryRepository.getBigGoalListByUserId(userInfoFromHeaderDto.getUserId());
        Users users = getUsers(userInfoFromHeaderDto);
        List<BigGoal> bigGoalList = bigGoalRepository.findAllByUsersAndCompleteStatusNotComp(users.getUserId());

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
    private float calculateBigGoalProgressByDto(List<SmallGoalResDto> smallGoalResDtoList){
        float bigGoalProgress = 0;
        int size = smallGoalResDtoList.size();

        // smallGoal이 없을 경우
        if(size == 0) return 0;

        // small goal
        for(int i = 0; i < size; i++){
            int completeDayCnt = 0;
            float smallGoalProgress = 0;
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

            if(completeDayCnt == 0 || totalDayCnt == 0){
                smallGoalProgress = 0;
            }
            else{
                smallGoalProgress = ((float)completeDayCnt / (float)totalDayCnt) * 100;
            }

            smallGoalResDtoList.get(i).setProgress(smallGoalProgress);

            bigGoalProgress += smallGoalProgress;
        }

        return (bigGoalProgress / (float)size);
    }

    private float calculateBigGoalProgressByEntity(List<SmallGoal> smallGoalList){
        float bigGoalProgress = 0;
        int size = smallGoalList.size();

        // smallGoal이 없을 경우
        if(size == 0) return 0;

        // small goal
        for(int i = 0; i < size; i++){
            int completeDayCnt = 0;
            float smallGoalProgress = 0;
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
                smallGoalProgress = ((float)completeDayCnt / (float)totalDayCnt) * 100;
            }

            bigGoalProgress += smallGoalProgress;
        }

        return (bigGoalProgress / (float)size);
    }





}
