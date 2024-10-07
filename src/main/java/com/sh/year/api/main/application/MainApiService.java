package com.sh.year.api.main.application;

import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.api.main.controller.dto.res.DelayRuleResDto;
import com.sh.year.api.main.controller.dto.res.MainResDto;
import com.sh.year.api.main.controller.dto.res.SmallGoalListForTodayAlertResDto;
import com.sh.year.domain.biggoal.biggoal.application.BigGoalService;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.rule.delayrule.application.DelayRuleService;
import com.sh.year.domain.smallgoal.smallgoal.application.SmallGoalService;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainApiService {

    private final BigGoalService bigGoalService;
    private final SmallGoalService smallGoalService;
    private final DelayRuleService delayRuleService;
    public MainResDto mainView(UserInfoFromHeaderDto userInfoFromTokenDto,
                               @PageableDefault(size = 5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){


        /**
         * 1. 완료된 BigGoal 제외한 BigGoal List 조회
         *
         * 2. 그 BigGoal들 중에서 오늘 알림이 필요한 애들 SmallGoal 리스트 조회
         *
         * 3. 완료된 BigGoal 제외하고 그리고 유예된 애들 조회
         *
         */
        // bigGoal 리스트 조회, 페이징(COMP 제외)
//        List<BigGoalMainResDto> bigGoalListForMain = bigGoalService.getBigGoalListForMain(userInfoFromTokenDto);
        List<BigGoalMainResDto> bigGoalListForMain = bigGoalService.getMainViewAPi(userInfoFromTokenDto, pageable);

        // bigGoal 전체 조회(COMP 제외)
        List<BigGoal> bigGoalList = bigGoalService.getBigGoalListForMain(userInfoFromTokenDto);

        // today smallGoal 리스트 조회
        List<SmallGoalListForTodayAlertResDto> todayAlertSmallGoalList = smallGoalService.getSmallGoalListForTodayAlert(bigGoalList);

        // 유예 리스트 조회
        List<DelayRuleResDto> delayGoalList = delayRuleService.getDelayruleList(userInfoFromTokenDto);

        // 생성
        MainResDto mainResDto = new MainResDto(bigGoalListForMain, todayAlertSmallGoalList, delayGoalList);

        // total progress
        float totalProgress = getTotalProgress(bigGoalListForMain);
        mainResDto.setTotalProgress(totalProgress);

        return mainResDto;
    }

    private float getTotalProgress(List<BigGoalMainResDto> bigGoalListForMain){

        float totalProgress = 0f;

        for(BigGoalMainResDto bigGoalMainResDto: bigGoalListForMain){
            totalProgress += Float.parseFloat(bigGoalMainResDto.getProgress());


        }

        return totalProgress / (float)bigGoalListForMain.size();
    }



}
