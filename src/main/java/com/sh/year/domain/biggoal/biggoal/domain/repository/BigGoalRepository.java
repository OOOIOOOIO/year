package com.sh.year.domain.biggoal.biggoal.domain.repository;

import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BigGoalRepository extends JpaRepository<BigGoal, Long> {
    List<BigGoal> findAllByUsers(Users users);

    List<BigGoal> findAllByUsers(Users users, Pageable pageable);

    // 전체 성공한
    @Query(value = "select bg " +
            "from BigGoal bg " +
            "where bg.users.userId = :userId " +
            "and bg.completeStatus = com.sh.year.domain.common.CompleteStatus.COMP")
    List<BigGoal> findAllCompleteBigGoalByUserId(@Param(value = "userId") Long userId);

    // 연도별
    @Query(value = "select bg " +
            "from BigGoal bg " +
            "where bg.users.userId = :userId " +
            "and bg.completeStatus = com.sh.year.domain.common.CompleteStatus.COMP " +
            "and bg.createdAt >= :startDate and bg.createdAt <= :endDate")
    List<BigGoal> findAllCompleteBigGoalByUserIdAndYear(@Param(value = "userId") Long userId,
                                                        @Param("startDate") LocalDateTime startDate,
                                                        @Param("endDate") LocalDateTime endDate);


    /*
    native쿼리 작성하기
   List<dto>로 프로젝션
select distinct *
from big_goal as bg
join small_goal as sg join big_goal b on b.big_goal_id = sg.big_goal_id
join rule as r on sg.small_goal_id = r.small_goal_id
join rule_alert_info rai on r.rule_id = rai.rule_id
where rai.year = 2024 and rai.month = 6;
and rai.alert_day IN (0x0001000001000001010000010000010100000100000101000001000001010000);

     */
}
