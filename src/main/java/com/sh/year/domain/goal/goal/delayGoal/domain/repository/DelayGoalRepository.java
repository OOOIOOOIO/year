package com.sh.year.domain.goal.goal.delayGoal.domain.repository;

import com.sh.year.domain.goal.goal.delayGoal.domain.DelayGoal;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DelayGoalRepository extends JpaRepository<DelayGoal, Long> {
    List<DelayGoal> findAllByUsers(Users users);

    Optional<DelayGoal> findByRule(Rule rule);
}
