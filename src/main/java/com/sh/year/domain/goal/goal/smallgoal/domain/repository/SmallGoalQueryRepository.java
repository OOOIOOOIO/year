package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;

import java.util.Optional;

public interface SmallGoalQueryRepository {

    Optional<SmallGoal> findSmallGoalById(long smallGoalId);
}
