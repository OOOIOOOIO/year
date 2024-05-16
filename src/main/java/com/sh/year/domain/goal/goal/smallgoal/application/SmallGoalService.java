package com.sh.year.domain.goal.goal.smallgoal.application;

import com.sh.year.domain.goal.goal.biggoal.api.dto.req.BigGoalReqDto;
import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.ShareStatus;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.RuleReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalUpdateReqDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.repository.RuleCompleteInfoRepository;
import com.sh.year.domain.goal.rule.rulerepeatday.domain.RuleRepeatDay;
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
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmallGoalService {

    private static final Integer DAILY_ALARM_STATE = 1;
    private static final Integer DAILY_REPEAT_SIZE = 7;
    private final BigGoalRepository bigGoalRepository;
    private final BigGoalQueryRepositoryImpl goalQueryRepository;
    private final SmallGoalRepository smallGoalRepository;
    private final SmallGoalQueryRepositoryImpl smallGoalQueryRepository;
    private final UsersRepository usersRepository;
    private final RuleCompleteInfoRepository ruleRepeatDatesRepository;




    /**
     * 작은목표 기본 조회
     */
    public BigGoalResDto getSmallGoalInfo(Long goalId, Integer routine){

        BigGoal bigGoal = goalQueryRepository.getBigGoalInfo(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

//        Rule rule = bigGoal.getRule();
//
//        int originalRoutine = bigGoal.getRule().getRoutine();
//
//        LocalDate endDate = bigGoal.getEndDate();
//
//        if(originalRoutine == 1){
//
//            RuleResDto ruleResDto = new RuleResDto(rule, DAILY_ALARM_STATE);
//
//            /**
//             * 매일이면 -> 시작일부터 오늘까지 / 시작일부터 endDate까지의 횟수
//             * 근데
//             */
//            calculateProgress(rule.getRoutine(), DAILY_REPEAT_SIZE, endDate);
//
////            new GoalResDto(goal, ruleResDto, progress)
//        }
//        else if (originalRoutine == 2) {
//
//        }
//        else{
//
//        }

//        new GoalResDto(goal, rule, progress)

        return null;
    }


    /**
     * 작은목표 저장
     */
    /**
     *
     * if choose foreach trim
     * 날짜계산, now~마지막월까지
     * 이번달의 며칠! -> 1, 3
     * 이번주의 무슨요일 -> 2
     *
     * 매일 -> 시작일 ~ 12/31까지 개수세기
     *
     * 매주
     *  -> 목표일이 오늘을 지나지 않은 경우 : 이번달 남은 주의 개수 + 12/31까지 남은 달의 개수 * 4
     *  -> 오늘이 목표일을 지난 경우 : 이번달 남은 주의 개수 -1 + 12/31까지 남은 달의 개수 * 4
     *      -> 보여줄 때 다음 걸 보여줘야하는데 흠흠 아으 머리아파....
     *
     * 매월
     *  -> 목표일이 오늘을 지나지 않은 경우 : (12/31까지 남은 달의 개수 + 1) * 4
     *  -> 오늘이 목표일을 지난 경우 : 12/31까지 남은 달의 개수 * 4
     */
    public Long saveSmallGoal(Long bigGoalId, SmallGoalReqDto smallGoalReqDto) {

        BigGoal bigGoal = goalQueryRepository.getBigGoalInfo(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        // smallGoal
        SmallGoal smallGoal = SmallGoal.createSmallGoal(smallGoalReqDto, bigGoal);


        // rule
        Rule rule = Rule.createRule(smallGoalReqDto.getRuleReqDto(), smallGoal);

        // ruleCompleteInfo
        RuleCompleteInfo ruleCompleteInfo = makeRuleCompleteInfo(smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());
        rule.addCompleteInfo(ruleCompleteInfo);


        // ruleRepeatDay
        int routine = smallGoalReqDto.getRuleReqDto().getRoutine();

        // (routine -> 1 : 매일, 2 : 매주, 3 : 매월)
        // 2, 3 : 특정일 필요
        if(routine == 1){ // routine == 1(매일)
            smallGoal.addRule(rule); // cascade(with goal)

        }
        else { // routine == 2 && 3(매주)
            List<Integer> ruleRepeatList = smallGoalReqDto.getRuleReqDto().getRuleRepeatList();

            for(Integer day : ruleRepeatList){
                RuleRepeatDay ruleRepeatDay = RuleRepeatDay.createRuleRepeatDay(day);
                rule.addRuleRepeatDay(ruleRepeatDay);

            }

            smallGoal.addRule(rule); // cascade(with goal)

        }

        SmallGoal savedSmallGoal = smallGoalRepository.save(smallGoal);

        return savedSmallGoal.getSmallGoalId();
    }


    /**
     * 작은목표 수정
     */
    public void updateSmallGoal(Long smallGoalId,  SmallGoalUpdateReqDto smallGoalUpdateReqDto){

        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        Rule rule = smallGoal.getRule(); // fetch join 생각

        rule.updateContents(smallGoalUpdateReqDto.getContents());

        smallGoal.updateSmallGoal(smallGoalUpdateReqDto);

    }


    /**
     * 작은목표 삭제
     */
    public void deleteSmallGoal(Long smallGoalId){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        smallGoalRepository.delete(smallGoal);
    }


    /**
     * 작은목표 달성여부 설정
     */
    public void updateCompleteStatus(Long smallGoalId){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        smallGoal.updateCompleteStatus(smallGoal.getCompleteStatus());
    }

    /**
     * 작은목표 리스트 보기
     */
    public void getSmallGoalList(UserInfoFromHeaderDto userInfoFromTokenDto){

    }




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
     *
     * dayOfWeek.getValue
     * 월(1) ~ 일(7)
     *
     * 매주일 경우!
     *
     * 우선 총 몇개월인지 구해야함
     *
     * - ChronoUnit.MONTHS.between(startDay, endDay) --> 탈락
     *      - start랑 end의 차이가 30일 미만일 경우 0
     *
     *
     *
     * 첫주
     * -> startDay ~~~ 일요일(7)까지 days가 있는지 확인
     *
     * 마지막주
     * -> 월요일(1) ~~~ end까지 days가 있는지 확인
     *
     * 그외 남은 개월 * dayList.size() 해서 남은 개월 수 계산!
     */

    private int getCountBetweenTwoDates(LocalDate startDay, LocalDate endDay, int routine, List<Integer> ruleRepeatList) {
        int alramDayCnt = 0;
        int totalDayCnt = getTotalDayCnt(startDay, endDay);

        if(routine == 2){ //매주

            if(getRestDayCnt(startDay, endDay) < 0){ // 2주 이하 -> 돌려주기
                for(int day = 0; day < totalDayCnt; day++){
                    int plusDay = startDay.plusDays(day).getDayOfWeek().getValue();

                    if(ruleRepeatList.contains(plusDay)) alramDayCnt++;
                }
            }
            else{ // 3주 이상 -> 첫주, 마지막주, 그 외
                int startDayValue = startDay.getDayOfWeek().getValue();
                int endDayValue = endDay.getDayOfWeek().getValue();

                //첫주
                for(int day = startDayValue; day <= 7; day++){

                    if(ruleRepeatList.contains(day)) alramDayCnt++;
                }


                //마지막주
                for(int day = endDayValue; day >= 1; day--){
                    if(ruleRepeatList.contains(day)) alramDayCnt++;
                }


                //그외
                int restDayCnt = getRestDayCnt(startDay, endDay);
                alramDayCnt += (restDayCnt * ruleRepeatList.size());


            }

        }
        else if(routine == 3){ //매월
            int monthValue = getMonthCntValue(startDay, endDay); // startDay ~ endDay 총 개월수

            /**
             * 매월일 경우!
             *
             * 첫달
             * -> startDay(create) ~~~ 마지막까지 day가 있는지 확인
             *
             * 마지막달
             * -> 1일 ~~~ endDate까지 day가 있는지 확인
             *
             * 그외 남은 개월 계산!
             *
             *
             * --> 오늘 ~ 이번달에 day가 있는지
             * --> 다음달 ~ endDate까지 몇달이 있는지 계산
             *      -> 마지막달에 day가 포함되는지 확인
             * => 두개 합치면 totalCnt
             *
             * 음 근데 5/28 ~ 6/5 이런 경우 달과 달이 섞여있으면 흠...
             * 이거 monthValue 차이가 0
             */

        }
        else{ //매일
            alramDayCnt = getTotalDayCnt(startDay, endDay);

        }

        return alramDayCnt;
    }

    private RuleCompleteInfo makeRuleCompleteInfo(int routine, List<Integer> ruleRepeatList){
        LocalDate now = LocalDate.now();
        LocalDate start = LocalDate.of( now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        LocalDate end = LocalDate.of( now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        int year = now.getYear();
        int month = now.getMonthValue();
        byte[] repeatDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서

        int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);


        return RuleCompleteInfo.createRepeatDates(year, month, repeatDays, totalDayCnt);

    }

    private int getMonthCntValue(LocalDate start, LocalDate end) {

        int monthA = start.getYear() * 12 + start.getMonthValue();
        int monthB = end.getYear() * 12 + end.getMonthValue();

        return monthB - monthA;
    }

    private int getTotalDayCnt(LocalDate start, LocalDate end){

        return (int)ChronoUnit.DAYS.between(start, end) + 1; //마지막날 포함
    }


    private int getRestDayCnt(LocalDate start, LocalDate end){
        LocalDate plusDays = start.plusDays((7 - start.getDayOfWeek().getValue()) + 1 ); // 일요일 나옴 / +1 해주면 다음주 월요일
        LocalDate minusDays = end.minusDays(end.getDayOfWeek().getValue());// 그 전주 일요일 나옴

        return (int)ChronoUnit.DAYS.between(plusDays, minusDays) / 6; // 6으로 나눠야 1주
    }


}
