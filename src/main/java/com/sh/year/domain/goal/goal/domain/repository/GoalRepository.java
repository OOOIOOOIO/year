package com.sh.year.domain.goal.goal.domain.repository;

import com.sh.year.domain.goal.goal.domain.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
