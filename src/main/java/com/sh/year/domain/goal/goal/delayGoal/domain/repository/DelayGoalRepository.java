package com.sh.year.domain.goal.goal.delayGoal.domain.repository;

import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.delayGoal.domain.DelayGoal;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DelayGoalRepository extends JpaRepository<DelayGoal, Long> {
    List<DelayGoal> findAllByUsers(Users users);

    @Query("select d " +
            "from DelayGoal d " +
            "where d.users.userId = :userId and d.completeStatus not in(com.sh.year.domain.goal.goal.common.CompleteStatus.COMP)")
    List<DelayGoal> findAllByUsersAndCompleteStatus(Long userId);

    Optional<DelayGoal> findByRule(Rule rule);
}
