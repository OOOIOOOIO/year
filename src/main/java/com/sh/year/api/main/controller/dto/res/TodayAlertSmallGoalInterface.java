package com.sh.year.api.main.controller.dto.res;


import java.time.LocalTime;

public interface TodayAlertSmallGoalInterface {

    Long getSmallGoalId();
    String getTitle();
    String getIcon();

    // rule
    Long getRuleId();
    int getRoutine(); // 매일 : 1, 매주 : 2, 매월 : 3
    String getContents();
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "kk:mm:ss", timezone = "Asia/Seoul")
    LocalTime getTimeAt();

    // rai
    byte[] getAlertDay();

    // rci
    byte[] getCompleteDay();

    int getTotalDayCnt();

}
