package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmallGoalRepository extends JpaRepository<SmallGoal, Long> {
}
