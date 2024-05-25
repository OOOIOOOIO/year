package com.sh.year.domain.goal.goal.biggoal.application;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.ShareStatus;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.repository.RuleCompleteInfoRepository;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class BigGoalService {

    private static final Integer DAILY_ALARM_STATE = 1;
    private static final Integer DAILY_REPEAT_SIZE = 7;
    private final BigGoalRepository bigGoalRepository;
    private final BigGoalQueryRepositoryImpl goalQueryRepository;
    private final UsersRepository usersRepository;
    private final RuleCompleteInfoRepository ruleRepeatDatesRepository;


    /**
     * 큰목표 기본 조회
     */
    public BigGoalResDto getBigGoalInfo(Long bigGoalId){

        BigGoal bigGoal = goalQueryRepository.getBigGoalInfo(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));


        return null;
    }

    /**
     * 큰목표 저장
     */
    public Long saveBigGoal(UserInfoFromHeaderDto userInfoFromHeaderDto, BigGoalReqDto bigGoalReqDto) {

        Users users = getUsers(userInfoFromHeaderDto);

        BigGoal bigGoal = BigGoal.createBigGoal(bigGoalReqDto, users);

        BigGoal savedBigGoal = bigGoalRepository.save(bigGoal);

        return savedBigGoal.getBigGoalId();
    }



    /**
     * 큰목표 수정
     */
    public void updateGoal(Long goalId, BigGoalReqDto bigGoalReqDto){

        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        // 공통 goal update
        bigGoal.updateBigGoal(bigGoalReqDto);

    }


    /**
     * 큰목표 삭제
     */
    public void deleteGoal(Long goalId){
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        bigGoalRepository.delete(bigGoal);

    }


    /**
     * 큰목표 공유여부 설정
     */
    public void updateShareStatus(Long goalId){
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        ShareStatus shareStatus = bigGoal.getShareStatus();
        bigGoal.updateShareStatus(shareStatus);

    }

    /**
     * 큰목표 달성여부 설정
     */
    public void updateCompleteStatus(Long goalId) {
        BigGoal bigGoal = bigGoalRepository.findById(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        CompleteStatus completeStatus = bigGoal.getCompleteStatus();
        bigGoal.updateCompleteStatus(completeStatus);

    }

    /**
     * 큰목표 리스트 보기
     */
    public void getGoalList(UserInfoFromHeaderDto userInfoFromTokenDto){

    }

    /**
     * 작은목표 리스트 보기
     */




    private Users getUsers(UserInfoFromHeaderDto userInfoFromHeaderDto) {

        return usersRepository.findById(userInfoFromHeaderDto.getUserId()).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
    }


    private boolean checkWeeklyAlarmState(){

        return true;
    }


    private boolean checkMonthlyAlarmState(){

        return true;
    }


    /**
     * 매일이면 -> 시작일부터 오늘까지 / 시작일부터 endDate까지의 횟수
     * 근데
     *
     * 딱 한번만 계산하면 될텐데
     */
    private Long calculateProgress(int routine, int repeatSize, LocalDate endDate){
        if(routine == 1){
            LocalDate now = LocalDate.now();

            int totalDates = getCountBetweenTwoDates(now, endDate);
//            int successDates = repository에서 가져오기, 0 예외처리하기


        }
        else if(routine == 2){

            /**
             * 시작일
             *
             *
             * 종료일
             *
             * 시작일 < 알람일 = +1
             * 시작일 > 알람일 = 0
             * 알람일 < 목표일 = +1
             * 알람일 > 목표일 = 0
             *
             * 시작일의 다음달 ~ 목표일의 전달 = +1씩
             *
             */


        }
        else{


        }


        return 1L;
    }

    private int getCountBetweenTwoDates(LocalDate startDate, LocalDate endDate) {

        return startDate.datesUntil(endDate) // 1일 ~ 2일 -> 1임. 목표이 포함 X. +1 해주기(목표일 포함)
                .collect(Collectors.toList()).size() + 1;

    }


    /**
     * 알람여부
     */
    public static void main(String[] args) {
        LocalDate start = LocalDate.of( 2023, 3, 1);
        LocalDate end = LocalDate.of( 2023, 3, 30);


        /**
         * 시작일 < 알람일 = +1
         * 시작일 > 알람일 = 0
         *
         * 알람일 < 목표일 = +1
         * 알람일 > 목표일 = 0
         *
         * 시작일의 다음달 ~ 목표일의 전달 = +1씩
         *
         * Progress를 성공 유무로 하는 게 맞나.
         *
         */
// ChronoUnit 을 이용한 두 날짜 사이 간격 구하기
        long diffMonth = ChronoUnit.MONTHS.between( start, end ); // 14
        System.out.println("diffMonth = " + diffMonth);
        long diffWeek = ChronoUnit.WEEKS.between( start, end ); // 65
        System.out.println("diffWeek = " + diffWeek);
        long diffDay = ChronoUnit.DAYS.between( start, end ); // 456

        System.out.println("diffDay = " + diffDay);
    }



}
