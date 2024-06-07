package com.sh.year.domain.goal.rule.rulecompleteinfo.domain;

import com.sh.year.domain.goal.rule.rule.domain.Rule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RuleCompleteInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repeatId;
    private int year;
    private int month;

    private byte[] completeDayArr;
    private int totalDayCnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruleId")
    private Rule rule;

    @Builder
    public RuleCompleteInfo(int year, int month, byte[] completeDayArr, int totalDayCnt) {
        this.year = year;
        this.month = month;
        this.completeDayArr = completeDayArr;
        this.totalDayCnt = totalDayCnt;
    }


    /**
     * 생성
     */
    public static RuleCompleteInfo createRepeatDates(int year, int month, byte[] completeDayArr, int totalDayCnt){
        return RuleCompleteInfo.builder()
                .year(year)
                .month(month)
                .completeDayArr(completeDayArr)
                .totalDayCnt(totalDayCnt)
                .build();
    }

    public void updateCompleteDayArr(byte[] completeDayArr){
        this.completeDayArr = completeDayArr;

    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }


}


/**
 * GET http://localhost:7777/api/goal/biggoal/2
 *
 * HTTP/1.1 200
 * X-Content-Type-Options: nosniff
 * X-XSS-Protection: 0
 * Cache-Control: no-cache, no-store, max-age=0, must-revalidate
 * Pragma: no-cache
 * Expires: 0
 * X-Frame-Options: DENY
 * Content-Type: application/json
 * Transfer-Encoding: chunked
 * Date: Thu, 30 May 2024 00:54:32 GMT
 * Keep-Alive: timeout=60
 * Connection: keep-alive
 *
 * {
 *   "title": "update test title1111",
 *   "contents": "update test contents11111",
 *   "category": "icon♥ 222222",
 *   "endDate": "2024-05-23",
 *   "shareStatus": 0,
 *   "completeStatus": 0,
 *   "smallGoalResDtoList": [
 *     {
 *       "smallGoalId": 1,
 *       "title": "3개월미만",
 *       "icon": "icon♥️",
 *       "endDate": "2024-06-26",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 1,
 *         "routine": 1,
 *         "timeAt": "18:12:00",
 *         "contents": "rule contents",
 *         "ruleRepeatList": []
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 2,
 *       "title": "루틴1 3개월이상",
 *       "icon": "icon♥️",
 *       "endDate": "2024-08-20",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 2,
 *         "routine": 1,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴1 3개월이상",
 *         "ruleRepeatList": []
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 3,
 *       "title": "루틴2 3개월미만",
 *       "icon": "icon♥️",
 *       "endDate": "2024-06-26",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 3,
 *         "routine": 2,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴2 3개월미만",
 *         "ruleRepeatList": [
 *           {
 *             "day": 1
 *           },
 *           {
 *             "day": 4
 *           }
 *         ]
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 4,
 *       "title": "루틴2 3개월이상",
 *       "icon": "icon♥️",
 *       "endDate": "2024-08-20",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 4,
 *         "routine": 2,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴2 3개월이상",
 *         "ruleRepeatList": [
 *           {
 *             "day": 2
 *           },
 *           {
 *             "day": 5
 *           },
 *           {
 *             "day": 6
 *           }
 *         ]
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 5,
 *       "title": "루틴2 3개월이상",
 *       "icon": "icon♥️",
 *       "endDate": "2024-08-20",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 5,
 *         "routine": 2,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴2 3개월이상",
 *         "ruleRepeatList": [
 *           {
 *             "day": 2
 *           },
 *           {
 *             "day": 5
 *           },
 *           {
 *             "day": 6
 *           }
 *         ]
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 6,
 *       "title": "루틴3 3개월미만 그냥",
 *       "icon": "icon♥️",
 *       "endDate": "2024-06-26",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 6,
 *         "routine": 3,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴3 3개월미만 그냥",
 *         "ruleRepeatList": [
 *           {
 *             "day": 15
 *           }
 *         ]
 *       },
 *       "progress": 0
 *     },
 *     {
 *       "smallGoalId": 7,
 *       "title": "Colum 조절 루틴2 3개월이상",
 *       "icon": "icon♥️",
 *       "endDate": "2024-08-20",
 *       "completeStatus": "FAIL",
 *       "ruleResDto": {
 *         "ruleId": 7,
 *         "routine": 2,
 *         "timeAt": "18:12:00",
 *         "contents": "루틴2 3개월이상",
 *         "ruleRepeatList": [
 *           {
 *             "day": 2
 *           },
 *           {
 *             "day": 5
 *           },
 *           {
 *             "day": 6
 *           }
 *         ]
 *       },
 *       "progress": 0
 *     }
 *   ],
 *   "progress": 0,
 *   "smallGoalCnt": 7
 * }
 */