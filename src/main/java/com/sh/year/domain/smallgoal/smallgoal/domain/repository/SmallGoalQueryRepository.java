package com.sh.year.domain.smallgoal.smallgoal.domain.repository;

import com.sh.year.domain.smallgoal.smallgoal.domain.SmallGoal;

import java.util.List;
import java.util.Optional;

public interface SmallGoalQueryRepository {

    Optional<SmallGoal> findSmallGoalBySmallGoalId(Long smallGoalId);
    List<SmallGoal> findSmallGoalListByBigGoalId(Long smallGoalId);

}
