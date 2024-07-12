package com.sh.year.api.main.application;

import com.sh.year.api.main.controller.dto.res.BigGoalMainResDto;
import com.sh.year.api.main.controller.dto.res.DelayGoalResDto;
import com.sh.year.api.main.controller.dto.res.MainResDto;
import com.sh.year.api.main.controller.dto.res.SmallGoalListForTodayAlertResDto;
import com.sh.year.domain.goal.goal.biggoal.application.BigGoalService;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.delayGoal.application.DelayGoalService;
import com.sh.year.domain.goal.goal.smallgoal.application.SmallGoalService;
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
    private final DelayGoalService delayGoalService;
    public MainResDto mainView(UserInfoFromHeaderDto userInfoFromTokenDto,
                               @PageableDefault(size = 5, sort="createdAt", direction = Sort.Direction.DESC) Pageable pageable){

        // bigGoal 리스트 조회, 페이징
//        List<BigGoalMainResDto> bigGoalListForMain = bigGoalService.getBigGoalListForMain(userInfoFromTokenDto);
        List<BigGoalMainResDto> bigGoalListForMain = bigGoalService.getBigGoalPaging(userInfoFromTokenDto, pageable);

        // bigGoal 전체 조회
        List<BigGoal> bigGoalList = bigGoalService.getBigGoalListForMain(userInfoFromTokenDto);
        // today smallGoal 리스트 조회
        List<SmallGoalListForTodayAlertResDto> todayAlertSmallGoalList = smallGoalService.getSmallGoalListForTodayAlert(bigGoalList);

        // 유예 리스트 조회
        List<DelayGoalResDto> delayGoalList = delayGoalService.getDelayGoalList(userInfoFromTokenDto);

        return new MainResDto(bigGoalListForMain, todayAlertSmallGoalList, delayGoalList);
    }



}
