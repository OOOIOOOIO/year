package com.sh.year.domain.goal.goal.smallgoal.application;

import com.sh.year.domain.goal.goal.biggoal.api.dto.res.BigGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalUpdateReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
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
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.List;


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
     * 작은목표 상세 조회
     */
    public SmallGoalResDto getSmallGoalInfo(Long smallGoalId){

//        BigGoal bigGoal = sma.getBigGoalInfo(goalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));
//
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
//
//        new GoalResDto(goal, rule, progress)

        return null;
    }

    /**
     * 작은목표 리스트 보기
     */
    public void getSmallGoalList(UserInfoFromHeaderDto userInfoFromTokenDto){

    }

    /**
     * 오늘 완료해야하는 리스트
     */


    /**
     * 작은목표 저장
     */
    public Long saveSmallGoal(Long bigGoalId, SmallGoalReqDto smallGoalReqDto) {

        BigGoal bigGoal = goalQueryRepository.getBigGoalInfo(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

        // smallGoal
        SmallGoal smallGoal = SmallGoal.createSmallGoal(smallGoalReqDto, bigGoal);

        // rule
        Rule rule = Rule.createRule(smallGoalReqDto.getRuleReqDto(), smallGoal);

        // ruleRepeatDay
        int routine = smallGoalReqDto.getRuleReqDto().getRoutine();

        // (routine -> 1 : 매일, 2 : 매주, 3 : 매월)
        // 2, 3 : 특정일 필요
        if(routine == 1){ // routine == 1(매일)
            // cascade(with rule)
            addRuleCompleteInfo(rule, smallGoalReqDto.getEndDate(), smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());

            smallGoal.addRule(rule); // cascade(with goal)

        }
        else { // routine == 2 && 3(매주)
            List<Integer> ruleRepeatList = smallGoalReqDto.getRuleReqDto().getRuleRepeatList();

            for(Integer day : ruleRepeatList){
                RuleRepeatDay ruleRepeatDay = RuleRepeatDay.createRuleRepeatDay(day);
                rule.addRuleRepeatDay(ruleRepeatDay);

            }

            // cascade(with rule)
            addRuleCompleteInfo(rule, smallGoalReqDto.getEndDate(), smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());

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
     * ======================================================================================================================================================
     * ======================================================================================================================================================
     *
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
     * 알람 totalCnt
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

                //사이
                int restDayCnt = getRestDayCnt(startDay, endDay);

                alramDayCnt += (restDayCnt * ruleRepeatList.size());

            }

        }
        else if(routine == 3){ //매월
            Integer day = ruleRepeatList.get(0);
            LocalDate targetDay = makeTargetDayForMonthly(day);

            if(isMoreThanTwoMonth(startDay, endDay)){ // 3달이상
                //첫달
                if(isInStartMonth(startDay, targetDay)) alramDayCnt++;

                //마지막달
                if(isInEndMonth(endDay, targetDay)) alramDayCnt++;

                //사이
                alramDayCnt += getRestMonthCnt(startDay, endDay);

            }
            else{ // 3달 미만
                //첫달
                if(isInStartMonth(startDay, targetDay)) alramDayCnt++;

                //마지막달
                if(isInEndMonth(endDay, targetDay)) alramDayCnt++;

            }
        }
        else{ //매일
            alramDayCnt = getTotalDayCnt(startDay, endDay);

        }

        return alramDayCnt;
    }


    /**
     * 1~마지막날 비트로 구분(과거 달성이력)
     *
     * 몇달인지만 알면 되지
     */
    private void addRuleCompleteInfo(Rule rule, LocalDate end, int routine, List<Integer> ruleRepeatList){
        byte[] repeatDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
        LocalDate start = LocalDate.now(); // 시작일
        int year = start.getYear();
        int startMonth = start.getMonthValue(); // 같을 때만 따로
        int endMonth = end.getMonthValue();

        if(routine == 3){ //매달
            int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);
            for(int month = startMonth; month <= endMonth; month++){
                rule.addCompleteInfo(RuleCompleteInfo.createRepeatDates(year, month, repeatDays, totalDayCnt));
            }
            return;
        }
        else if(routine == 2){ //매주
            int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);
            for(int month = startMonth; month <= endMonth; month++){
                rule.addCompleteInfo(RuleCompleteInfo.createRepeatDates(year, month, repeatDays, totalDayCnt));
            }
            return;
        }

        //매일
        int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);
        for(int month = startMonth; month <= endMonth; month++){
            rule.addCompleteInfo(RuleCompleteInfo.createRepeatDates(year, month, repeatDays, totalDayCnt));
        }

    }


    /**
     * 매달일 경우
     *
     * targetDay 만들어주기
     */
    private LocalDate makeTargetDayForMonthly(Integer day) {
        LocalDate now = LocalDate.now();
        LocalDate targetDay;

        if(day == -1){ // 마지막날인 경우
            YearMonth yearMonth = YearMonth.from(now);
            targetDay = yearMonth.atEndOfMonth();
        }
        else{
            int year = now.getYear();
            int month = now.getMonthValue();

            targetDay = LocalDate.of(year, month, day);
        }

        if(targetDay.isAfter(now) || targetDay.isEqual(now)) return targetDay; // start보다 후에 있을 경우 이번달 n일로 설정

        return targetDay.plusMonths(1); // start보다 전에 있을 경우 다음달 n일로 설정
    }

    /**
     * 매달일 경우
     *
     * 첫달, 마지막달 제외 사이 몇개월이 있는지
     */
    private int getRestMonthCnt(LocalDate start, LocalDate end) {

        int monthA = start.getYear() * 12 + start.getMonthValue();
        int monthB = end.getYear() * 12 + end.getMonthValue();

        return (monthB - monthA) + 1; // 5/1 ~ 5/31 : 0으로 나옴 +1 해주기
    }

    /**
     * 매달일 경우
     *
     * 첫달에 targetDay가 있는지
     */
    private boolean isInStartMonth(LocalDate start, LocalDate targetDay){
        YearMonth startYearMonth = YearMonth.from(start);
        LocalDate endOfMonth = startYearMonth.atEndOfMonth();

        if((start.isBefore(targetDay) || start.isEqual(targetDay)) && (endOfMonth.isAfter(targetDay) || endOfMonth.isEqual(targetDay))) return true;

        return false;
    }

    /**
     * 매달일 경우
     *
     * 마지막달에 targetDay가 있는지
     */
    private boolean isInEndMonth(LocalDate end, LocalDate targetDay){
        YearMonth endYearMonth = YearMonth.from(end);
        LocalDate startOfMonth = endYearMonth.atDay(1);

        if((startOfMonth.isBefore(targetDay) || startOfMonth.isEqual(targetDay)) && (end.isAfter(targetDay) || end.isEqual(targetDay))) return true;

        return false;

    }

    /**
     * 매달일 경우
     */
    private boolean isMoreThanTwoMonth(LocalDate start, LocalDate end) {
        int startYear = start.getYear();
        int startMonth = start.getMonthValue();

        int endYear = end.getYear();
        int endMonth = end.getMonthValue();

        YearMonth startYearMonth = YearMonth.of(startYear, startMonth);
        YearMonth endYearMonth = YearMonth.of(endYear, endMonth);

        LocalDate nextMonthFirstDayFromStartDay = startYearMonth.atEndOfMonth().plusDays(1); // 다음달 첫날
        LocalDate beforeMonthLastDayFromEndDay = endYearMonth.atDay(1).minusDays(1); // 이전달 마지막날

        return nextMonthFirstDayFromStartDay.isBefore(beforeMonthLastDayFromEndDay);
    }


    /**
     * 시작일 ~ 마감일 총 날짜수
     *
     * 매일일 경우 사용
     */
    private int getTotalDayCnt(LocalDate start, LocalDate end){

        return (int)ChronoUnit.DAYS.between(start, end) + 1; //마지막날 포함
    }


    /**
     * 매주일 경우
     */
    private int getRestDayCnt(LocalDate start, LocalDate end){
        LocalDate plusDays = start.plusDays((7 - start.getDayOfWeek().getValue()) + 1 ); // 일요일 나옴 / +1 해주면 다음주 월요일
        LocalDate minusDays = end.minusDays(end.getDayOfWeek().getValue());// 그 전주 일요일 나옴

        return (int)ChronoUnit.DAYS.between(plusDays, minusDays) / 6; // 6으로 나눠야 1주
    }


}
