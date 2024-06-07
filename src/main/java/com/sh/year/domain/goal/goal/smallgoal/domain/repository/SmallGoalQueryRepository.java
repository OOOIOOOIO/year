package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;

import java.util.List;
import java.util.Optional;

public interface SmallGoalQueryRepository {

    Optional<SmallGoal> findSmallGoalBySmallGoalId(Long smallGoalId);
    List<SmallGoal> findSmallGoalListByBigGoalId(Long smallGoalId);
}
