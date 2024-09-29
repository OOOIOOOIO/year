package com.sh.year.goal.repository;


import com.sh.year.domain.smallgoal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.smallgoal.smallgoal.domain.repository.SmallGoalQueryRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
public class SmallGoalRepositoryTest {

    @Autowired
    SmallGoalQueryRepositoryImpl smallGoalQueryRepository;

    @DisplayName("Small Goal Routine 1, 상세조회")
    @Test
    public void findSmallGoalRoutine1ById(){
        // given
        Long smallGoalIdUnderThan3 = 1L; // Routine 1, 3개월 미만
        Long smallGoalIdMoreThan3 = 2L; // Routine 1, 3개월 이상

        // when
        Optional<SmallGoal> smallGoalById = smallGoalQueryRepository.findSmallGoalBySmallGoalId(smallGoalIdUnderThan3);

        // then

    }

    @Test
    public void updateCompleteInfo(){
        // given


        // when

        // then

    }
}
