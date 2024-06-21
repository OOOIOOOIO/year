package com.sh.year.domain.goal.goal.smallgoal.application;

import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulealertinfo.domain.RuleAlertInfo;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class temp {

    public static void main(String[] args) {

        LocalDate startDay = LocalDate.now(); // 시작일
        int startYear = startDay.getYear();
        int startMonth = startDay.getMonthValue(); // 같을 때만 따로
//        int endMonth = endDay.getMonthValue();
        int endMonth = LocalDate.now().getMonthValue();
        LocalDate endDay = LocalDate.of(2024, 10,10);


        LocalDate mondayOfMonth = YearMonth.of(startYear, startMonth).atDay(1); //1일구하기

        List<Integer> ruleRepeatList = new ArrayList<>();
//        ruleRepeatList.add(1);
//        ruleRepeatList.add(3);
//        ruleRepeatList.add(7);
        ruleRepeatList.add(12);


        List<LocalDate> dateList = new ArrayList<>();


        // 1~31 or -1
        LocalDate target = startDay;
        // endDay 전까지 7씩 더해주면서 쭉 넣어준다
        while(target.isBefore(endDay)){
            dateList.add(target);
            target = target.plusDays(1);
        }


        // 날짜순으로 정렬해준다
        Collections.sort(dateList);

        //이후 해당 월에 맞춰 rai를 만들어준다.
        Map<Month, List<LocalDate>> groupedDates = groupDatesByMonth(dateList);

        // 출력 예시
        for (Month month : groupedDates.keySet()) {
            System.out.println();
            System.out.println(month.getValue() + ": " );
            groupedDates.get(month).stream().forEach(System.out::println);
        }


    }

    /**
     * 월 별로 그룹화
     */
    private static Map<Month, List<LocalDate>> groupDatesByMonth(List<LocalDate> dateList) {
        return dateList.stream()
                .collect(Collectors.groupingBy(LocalDate::getMonth));
    }




    /**
     * 1~마지막날 비트로 구분(과거 달성이력)
     *
     */
    private void addRuleAlertInfo(Rule rule, LocalDate endDay, int routine, List<Integer> ruleRepeatList){
        byte[] repeatDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
        LocalDate startDay = LocalDate.now(); // 시작일
        int startYear = startDay.getYear();
        int startMonth = startDay.getMonthValue(); // 같을 때만 따로
        int endMonth = endDay.getMonthValue();



        if(routine == 3){ //매달
            List<LocalDate> dateList = new ArrayList<>();

            // 1~31 or -1
            int targetDay = ruleRepeatList.get(0);
            LocalDate target = null;
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
                byte[] weeklyAlertDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
                List<LocalDate> alertDayList = groupedDates.get(month);

                System.out.println(month + ": " + groupedDates.get(month));

                for (LocalDate day : alertDayList) {
                    int dayOfMonth = day.getDayOfMonth();

                    repeatDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, weeklyAlertDays));
            }


        }
        else if(routine == 2){ //매주

            List<LocalDate> dateList = new ArrayList<>();

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

                    repeatDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, weeklyAlertDays));
            }

        }
        else{ //매일
            List<LocalDate> dateList = new ArrayList<>();

            // 1~31 or -1
            LocalDate target = startDay;
            // endDay 전까지 7씩 더해주면서 쭉 넣어준다
            while(target.isBefore(endDay)){
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
                byte[] weeklyAlertDays = new byte[32]; // 저장한 날부터(32개 고정 상관없음 1의 개수를 셀거라서
                List<LocalDate> alertDayList = groupedDates.get(month);

                System.out.println(month + ": " + groupedDates.get(month));

                for (LocalDate day : alertDayList) {
                    int dayOfMonth = day.getDayOfMonth();

                    repeatDays[dayOfMonth] = 1;
                }

                rule.addAlertInfo(RuleAlertInfo.createRuleAlertInfo(startYear, monthValue, weeklyAlertDays));
            }

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
