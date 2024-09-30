package com.sh.year.goal.repository;

import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.biggoal.biggoal.domain.repository.BigGoalRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class BigGoalRepositoryTest {

    @Autowired
    BigGoalRepository bigGoalRepository;


    @DisplayName("특정 연도 조회")
    @Test
    public void findAllByUsersAndCompleteStatusIsComplete(){
        // given
        Long userId = 1L;
//        int year = LocalDate.now().getYear();
        int year = 2024;

        LocalDateTime startDate = LocalDateTime.of(year, 1, 1, 0, 0, 1);
        LocalDateTime endDate = LocalDateTime.of(year, 12, 31, 23, 59, 0);

        // when
        List<BigGoal> allByUsersAndCompleteStatusIsComplete = bigGoalRepository.findAllCompleteBigGoalByUserIdAndYear(userId, startDate, endDate);

        // then
        assertThat(allByUsersAndCompleteStatusIsComplete.size()).isEqualTo(3);

    }

    @DisplayName("지금까지 전체 성공한 큰목표들 조회")
    @Test
    public void findAllCompleteBigGoalByUserId(){
        // given
        Long userId = 1L;

        // when
        List<BigGoal> allCompleteBigGoalByUserId = bigGoalRepository.findAllCompleteBigGoalByUserId(userId);

        // then
        assertThat(allCompleteBigGoalByUserId.size()).isEqualTo(1);

    }


}
