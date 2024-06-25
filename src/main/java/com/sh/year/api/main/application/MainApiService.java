package com.sh.year.api.main.application;

import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.api.main.controller.dto.res.DelayGoalResDto;
import com.sh.year.api.main.controller.dto.res.MainResDto;
import com.sh.year.api.main.controller.dto.res.TodayAlertSmallGoalResDto;
import com.sh.year.domain.goal.goal.biggoal.application.BigGoalService;
import com.sh.year.domain.goal.goal.delayGoal.application.DelayGoalService;
import com.sh.year.domain.goal.goal.smallgoal.application.SmallGoalService;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainApiService {

    private final BigGoalService bigGoalService;
    private final SmallGoalService smallGoalService;
    private final DelayGoalService delayGoalService;
    public MainResDto mainView(UserInfoFromHeaderDto userInfoFromTokenDto){

        /**
         * today에 해당하는 rule 리스트 조회
         *
         * 유예 rule 리스트 조회
         *
         * bigGoal 리스트 조회
         *
         */
        // bigGoal 리스트 조회
        List<BigGoalMainResDto> goalListForAlert = bigGoalService.getGoalListForAlert(userInfoFromTokenDto);

        // today smallGoal 리스트 조회
        List<TodayAlertSmallGoalResDto> todayAlertSmallGoalList = smallGoalService.getTodayAlertSmallGoalList();

        // 유예 리스트 조회
        List<DelayGoalResDto> delayGoalList = delayGoalService.getDelayGoalList(userInfoFromTokenDto);

        return new MainResDto(goalListForAlert, todayAlertSmallGoalList, delayGoalList);
    }



}
