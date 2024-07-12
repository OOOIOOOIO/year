package com.sh.year.domain.goal.goal.smallgoal.application;

import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.api.main.controller.dto.res.SmallGoalListForTodayAlertResDto;
import com.sh.year.api.main.controller.dto.res.TodayAlertSmallGoalInterface;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.repository.BigGoalRepository;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.req.SmallGoalUpdateReqDto;
import com.sh.year.domain.goal.goal.smallgoal.api.dto.res.SmallGoalResDto;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalQueryRepositoryImpl;
import com.sh.year.domain.goal.goal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleQueryRepositoryImpl;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import com.sh.year.domain.goal.rule.rulealertinfo.domain.RuleAlertInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import com.sh.year.domain.goal.rule.rulerepeatday.domain.RuleRepeatDay;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class SmallGoalService {

    private final BigGoalRepository bigGoalRepository;
    private final SmallGoalRepository smallGoalRepository;
    private final SmallGoalQueryRepositoryImpl smallGoalQueryRepository;
    private final RuleQueryRepositoryImpl ruleQueryRepository;
    private final RuleRepository ruleRepository;



    /**
     * 작은목표 상세 조회
     */
    @LogTrace
    public SmallGoalResDto getSmallGoalInfo(Long smallGoalId){

        SmallGoal smallGoal = smallGoalQueryRepository.findSmallGoalBySmallGoalId(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        SmallGoalResDto smallGoalResDto = new SmallGoalResDto(smallGoal);

        List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalResDto.getRuleResDto().getRuleCompleteInfoDtoList();

        float progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

        if(checkCompleteStatus(ruleCompleteInfoDtoList)){
            smallGoalResDto.getRuleResDto().setCompleteStatus(1);
        }
        else{
            smallGoalResDto.getRuleResDto().setCompleteStatus(0);
        }


        smallGoalResDto.setProgress(progress);

        return smallGoalResDto;
    }


    /**
     * 작은목표 리스트 보기
     *
     *
     * --> 필요 없는 것 같은데
     */
    @LogTrace
    public List<SmallGoalResDto> getSmallGoalList(Long bigGoalId){

        List<SmallGoal> smallGoalList = smallGoalQueryRepository.findSmallGoalListByBigGoalId(bigGoalId);

        List<SmallGoalResDto> smallGoalResDtoList = new ArrayList<>();

        for (SmallGoal smallGoal : smallGoalList) {
            SmallGoalResDto smallGoalResDto = new SmallGoalResDto(smallGoal);

            List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalResDto.getRuleResDto().getRuleCompleteInfoDtoList();

            float progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

            smallGoalResDto.setProgress(progress);

            smallGoalResDtoList.add(smallGoalResDto);
        }

        return smallGoalResDtoList;

    }

    /**
     * 오늘자 small Goal 확인하기
     */
    @LogTrace
    public List<SmallGoalListForTodayAlertResDto> getSmallGoalListForTodayAlert(List<BigGoal> bigGoalList){
        List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList = new ArrayList<>();

        LocalDate localDate = LocalDate.now();
        int today = localDate.getDayOfMonth();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();

        for (BigGoal bigGoal : bigGoalList) {
            Long bigGoalId = bigGoal.getBigGoalId();

            List<TodayAlertSmallGoalInterface> todayAlertGoalList = smallGoalRepository.getTodayAlertGoalList(bigGoalId, year, month);

            for (TodayAlertSmallGoalInterface todaySmallGoal : todayAlertGoalList) {

                // 6월 중 오늘에 해당하는 애들이 있는지 확인하기
                // 있다면, rpd 가져와서 넣고, rci 가져와서 process 계산 넣기
                byte[] alertDay = todaySmallGoal.getAlertDay();
                byte[] completeDay = todaySmallGoal.getCompleteDay();
                if(alertDay[today] == 1 && completeDay[today] != 1){
                    Rule rule = ruleRepository.findById(todaySmallGoal.getRuleId()).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

                    SmallGoalListForTodayAlertResDto smallGoalListForTodayAlertResDto = new SmallGoalListForTodayAlertResDto(todaySmallGoal, rule);

                    List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalListForTodayAlertResDto.getRuleResDto().getRuleCompleteInfoDtoList();

                    float progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

                    smallGoalListForTodayAlertResDto.setProgress(progress);

                    smallGoalListForTodayAlertResDtoList.add(smallGoalListForTodayAlertResDto);
                }


            }
        }

        return smallGoalListForTodayAlertResDtoList;

    }


    /**
     * 작은목표 저장
     */
    @LogTrace
    public Long saveSmallGoal(Long bigGoalId, SmallGoalReqDto smallGoalReqDto) {

        // bigGoal
        BigGoal bigGoal = bigGoalRepository.findById(bigGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistBigGoal));

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
            addRuleAlertInfo(rule, smallGoalReqDto.getEndDate(), smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());

        }
        else { // routine == 2 && 3(매주)
            List<Integer> ruleRepeatList = smallGoalReqDto.getRuleReqDto().getRuleRepeatList();

            for(Integer day : ruleRepeatList){
                RuleRepeatDay ruleRepeatDay = RuleRepeatDay.createRuleRepeatDay(day);
                rule.addRuleRepeatDay(ruleRepeatDay);

            }

            // cascade(with rule)
            addRuleCompleteInfo(rule, smallGoalReqDto.getEndDate(), smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());
            addRuleAlertInfo(rule, smallGoalReqDto.getEndDate(), smallGoalReqDto.getRuleReqDto().getRoutine(), smallGoalReqDto.getRuleReqDto().getRuleRepeatList());

        }

        smallGoal.addRule(rule); // cascade(with goal)

        SmallGoal savedSmallGoal = smallGoalRepository.save(smallGoal);

        return savedSmallGoal.getSmallGoalId();
    }


    /**
     * 작은목표 수정
     */
    @LogTrace
    public void updateSmallGoal(Long smallGoalId,  SmallGoalUpdateReqDto smallGoalUpdateReqDto){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        smallGoal.updateSmallGoal(smallGoalUpdateReqDto);

    }


    /**
     * 작은목표 삭제
     */
    @LogTrace
    public void deleteSmallGoal(Long smallGoalId){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        smallGoalRepository.delete(smallGoal);
    }


    /**
     * 작은목표 달성여부 설정(100% 달성시)
     */
    @LogTrace
    public void updateCompleteStatus(Long smallGoalId){
        SmallGoal smallGoal = smallGoalRepository.findById(smallGoalId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistSmallGoal));

        smallGoal.updateCompleteStatus(smallGoal.getCompleteStatus());

    }

    /**
     * 작은목표의 규칙 달성여부 설정
     */
    @LogTrace
    public void updateRuleCompleteInfo(Long ruleId) {
        LocalDate now = LocalDate.now();

        Rule rule = ruleQueryRepository.findRuleAndRuleCompleteInfo(now.getYear(), now.getMonth().getValue(), ruleId).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

        int today = now.getDayOfMonth();
        byte[] completeDayArr = rule.getRuleCompleteInfoList().get(0).getCompleteDay();

        completeDayArr[today] = 1;

        rule.getRuleCompleteInfoList().get(0).updateCompleteDayArr(completeDayArr);

    }


    /**
     * ======================================================================================================================================================
     * ======================================================================================================================================================
     *
     */



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
     * samll goal 상세, rule 달성했는지 확인
     */
    private boolean checkCompleteStatus(List<RuleCompleteInfoDto> ruleCompleteInfoDtoList){
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        for (RuleCompleteInfoDto rci : ruleCompleteInfoDtoList) {
            int targetYear = rci.getYear();
            int targetMonth = rci.getMonth();

            if(targetYear == year && targetMonth == month){

                if(rci.getCompleteDay()[day] == 1){
                    return true;
                }
                else{
                    break;
                }
            }

        }

        return false;
    }


    /**
     * 알람 totalDayCnt
     */
    private int getCountBetweenTwoDates(LocalDate startDay, LocalDate endDay, int routine, List<Integer> ruleRepeatList) {
        int alramDayCnt = 0;
        int totalDayCnt = getTotalDayCnt(startDay, endDay);

        if(routine == 2){ //매주

            if(getTotalDayCntForWeekly(startDay, endDay) < 0){ // 2주 이하 -> 돌려주기
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
                int restDayCnt = getTotalDayCntForWeekly(startDay, endDay);

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
     * 1~마지막날 비트로 구분(달성이력 생성)
     *
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
                rule.addCompleteInfo(RuleCompleteInfo.createRuleCompleteInfo(year, month, repeatDays, totalDayCnt));
            }
            return;
        }
        else if(routine == 2){ //매주
            int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);
            for(int month = startMonth; month <= endMonth; month++){
                rule.addCompleteInfo(RuleCompleteInfo.createRuleCompleteInfo(year, month, repeatDays, totalDayCnt));
            }
            return;
        }

        //매일
        int totalDayCnt = getCountBetweenTwoDates(start, end, routine, ruleRepeatList);
        for(int month = startMonth; month <= endMonth; month++){
            rule.addCompleteInfo(RuleCompleteInfo.createRuleCompleteInfo(year, month, repeatDays, totalDayCnt));
        }

    }

    /**
     * 1~마지막날 비트로 구분(과거 달성이력)
     *
     */
    private void addRuleAlertInfo(Rule rule, LocalDate endDay, int routine, List<Integer> ruleRepeatList){
        LocalDate startDay = LocalDate.now(); // 시작일
        int startYear = startDay.getYear();
        int startMonth = startDay.getMonthValue(); // 같을 때만 따로

        List<LocalDate> dateList = new ArrayList<>();


        if(routine == 3){ //매달

            // 1~31 or -1
            int targetDay = ruleRepeatList.get(0);
            LocalDate target;

            if(targetDay == -1){ //마지막날
                target = YearMonth.of(startYear, startMonth).atEndOfMonth();
            }
            else{
                target = LocalDate.of(startYear, startMonth, targetDay);

            }

            // endDay 전까지 7씩 더해주면서 쭉 넣어준다
            while(target.isBefore(endDay)){
                dateList.add(target);
                target = target.plusMonths(1);
            }

            // 날짜순으로 정렬해준다
            Collections.sort(dateList);

            //이후 해당 월에 맞춰 rai를 만들어준다.
            Map<Month, List<LocalDate>> groupedDates = groupDatesByMonth(dateList);

            // 월 별로 돌려서 마킹하기
            for (Month month : groupedDates.keySet()) {
                int monthValue = month.getValue();
                byte[] monthlyAlertDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
                List<LocalDate> alertDayList = groupedDates.get(month);

                System.out.println(month + ": " + groupedDates.get(month));

                for (LocalDate day : alertDayList) {
                    int dayOfMonth = day.getDayOfMonth();

                    monthlyAlertDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, monthlyAlertDays));
            }


        }
        else if(routine == 2){ //매주

            // month의 시작일(1일) 부터 7 더한 날들 구하기
            for(int i = 0; i < 7; i++){
                LocalDate target = YearMonth.of(startYear, startMonth).atDay(1).plusDays(i);

                // 요일 확인해서 날짜값 구하기
                int targetDayValue = target.getDayOfWeek().getValue();

                // ruleRepeatList에 있다면
                if(ruleRepeatList.contains(targetDayValue)) {
                    // endDay 전까지 7씩 더해주면서 쭉 넣어준다
                    while(target.isBefore(endDay)){
                        dateList.add(target);
                        target = target.plusDays(7);
                    }

                }
            }

            // 날짜순으로 정렬해준다
            Collections.sort(dateList);

            //이후 해당 월에 맞춰 rai를 만들어준다.
            Map<Month, List<LocalDate>> groupedDates = groupDatesByMonth(dateList);

            // 월 별로 돌려서 마킹하기
            for (Month month : groupedDates.keySet()) {
                int monthValue = month.getValue();
                byte[] weeklyAlertDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
                List<LocalDate> alertDayList = groupedDates.get(month);

                for(LocalDate day : alertDayList){
                    int dayOfMonth = day.getDayOfMonth();

                    weeklyAlertDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, weeklyAlertDays));
            }

        }
        else{ //매일
            LocalDate target = startDay;

            /**
             * 첫날, 마지막날 안들어가지네
             */
            // endDay 전까지 1씩 더해주면서 쭉 넣어준다
            while(target.isEqual(endDay) || target.isBefore(endDay)){
                dateList.add(target);
                target = target.plusDays(1);
            }

            // 날짜순으로 정렬해준다
            Collections.sort(dateList);

            //이후 해당 월에 맞춰 rai를 만들어준다.
            Map<Month, List<LocalDate>> groupedDates = groupDatesByMonth(dateList);

            // 월 별로 돌려서 마킹하기
            for (Month month : groupedDates.keySet()) {
                int monthValue = month.getValue();
                byte[] dailyAlertDays = new byte[32];
                List<LocalDate> alertDayList = groupedDates.get(month);

                for (LocalDate day : alertDayList) {
                    int dayOfMonth = day.getDayOfMonth();

                    dailyAlertDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, dailyAlertDays));
            }

        }

    }

    private Map<Month, List<LocalDate>> groupDatesByMonth(List<LocalDate> dateList) {
        return dateList.stream()
                .collect(Collectors.groupingBy(LocalDate::getMonth));
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

        LocalDate firstDayOfNextMonthFromStartDay = startYearMonth.atEndOfMonth().plusDays(1); // 다음달 첫날
        LocalDate lastOfPrevMonthDayFromEndDay = endYearMonth.atDay(1).minusDays(1); // 이전달 마지막날

        return firstDayOfNextMonthFromStartDay.isBefore(lastOfPrevMonthDayFromEndDay);
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
    private int getTotalDayCntForWeekly(LocalDate start, LocalDate end){
        LocalDate plusDays = start.plusDays((7 - start.getDayOfWeek().getValue()) + 1 ); // 일요일 나옴 / +1 해주면 다음주 월요일
        LocalDate minusDays = end.minusDays(end.getDayOfWeek().getValue());// 그 전주 일요일 나옴

        return (int)ChronoUnit.DAYS.between(plusDays, minusDays) / 6; // 6으로 나눠야 1주
    }


}
