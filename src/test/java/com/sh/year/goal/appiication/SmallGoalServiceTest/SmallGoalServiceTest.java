package com.sh.year.goal.appiication.SmallGoalServiceTest;

import com.sh.year.domain.goal.goal.smallgoal.application.SmallGoalService;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootTest
public class SmallGoalServiceTest {

    @Autowired
    SmallGoalService smallGoalService;


    @DisplayName("update completeinfo")
    @Test
    @Transactional
    public void updateRuleCompleteInfo(){
        smallGoalService.updateRuleCompleteInfo(7L);
    }
}
