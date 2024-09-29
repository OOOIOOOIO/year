package com.sh.year.goal.repository;

import com.sh.year.api.main.controller.dto.res.SmallGoalListForTodayAlertResDto;
import com.sh.year.api.main.controller.dto.res.TodayAlertSmallGoalInterface;
import com.sh.year.domain.smallgoal.smallgoal.domain.repository.SmallGoalRepository;
import com.sh.year.domain.rule.rule.domain.Rule;
import com.sh.year.domain.rule.rule.domain.repository.RuleRepository;
import com.sh.year.domain.rule.rulecompleteinfo.dto.RuleCompleteInfoDto;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
public class MainTest {

    @Autowired
    SmallGoalRepository smallGoalRepository;

    @Autowired
    RuleRepository ruleRepository;

    @Test
    @Transactional
    public void getTodayAlertSmallGoalListTest(){
        // given
//        byte[] alertDay = new byte[]{
//                1,1,1,1,1,1,1,1,
//                1,1,1,1,1,1,1,1,
//                1,1,1,1,1,1,1,1,
//                1,1,1,1,1,1,1,1};
        byte[] alertDay = new byte[]{
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,1,0,0,0,
                0,0,0,0,0,0,0,0,};
        List<SmallGoalListForTodayAlertResDto> smallGoalListForTodayAlertResDtoList = new ArrayList<>();
        List<TodayAlertSmallGoalInterface> todayAlertGoalList = smallGoalRepository.getTodayAlertGoalList(1L,2024, 6);
        LocalDate localDate = LocalDate.now();
        int dayOfMonth = localDate.getDayOfMonth();
        // when
        // 6월에 해당하는 애들 쭉 뽑아오기
        for (TodayAlertSmallGoalInterface today : todayAlertGoalList) {

            // 6월 중 오늘에 해당하는 애들이 있는지 확인하기
            // 있다면, rpd 가져와서 넣고, rci 가져와서 process 계산 넣기
            byte[] alertDay1 = today.getAlertDay();
            if(alertDay1[dayOfMonth] == 1){
                Rule rule = ruleRepository.findById(today.getRuleId()).orElseThrow(() -> new CustomException(CustomErrorCode.NotExistRule));

                SmallGoalListForTodayAlertResDto smallGoalListForTodayAlertResDto = new SmallGoalListForTodayAlertResDto(today, rule);

                List<RuleCompleteInfoDto> ruleCompleteInfoDtoList = smallGoalListForTodayAlertResDto.getRuleResDto().getRuleCompleteInfoDtoList();

//                int progress = calculateProgress(ruleCompleteInfoDtoList, ruleCompleteInfoDtoList.get(0).getTotalDayCnt());

//                todayAlertGoalResDto.setProgress(progress);

                smallGoalListForTodayAlertResDtoList.add(smallGoalListForTodayAlertResDto);
            }

        }

        log.info("size : " + smallGoalListForTodayAlertResDtoList.size());


    }




}
