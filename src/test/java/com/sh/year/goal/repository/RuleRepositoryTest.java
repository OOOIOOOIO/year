package com.sh.year.goal.repository;

import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleQueryRepositoryImpl;
import com.sh.year.domain.goal.rule.rule.domain.repository.RuleRepository;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.repository.RuleCompleteInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class RuleRepositoryTest {

    @Autowired
    RuleCompleteInfoRepository ruleCompleteInfoRepository;

    @Autowired
    RuleQueryRepositoryImpl ruleQueryRepository;

    @Autowired
    RuleRepository ruleRepository;

    @DisplayName("update complete info")
    @Test
    public void updateRuleCompleteInfo(){
        // given
        int year = 2024;
        int month = 5;
        Long ruleId = 7L;

        // when
//        RuleCompleteInfo ruleCompleteInfo = ruleCompleteInfoRepository.findByYearAndMonthAndRule(year, month, rule).get();
        Rule rule = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(year, month, ruleId).get();

        Long ruleId1 = rule.getRuleId();

        List<RuleCompleteInfo> ruleCompleteInfoList = rule.getRuleCompleteInfoList();

        System.out.println("ruleCompleteInfoList = " + ruleCompleteInfoList.size());
        for (RuleCompleteInfo ruleCompleteInfo : ruleCompleteInfoList) {
            System.out.println("ruleCompleteInfo = " + ruleCompleteInfo.getRepeatId());
            System.out.println("ruleCompleteInfo = " + ruleCompleteInfo.getYear());
            System.out.println("ruleCompleteInfo = " + ruleCompleteInfo.getMonth());
        }


    }

}
