package com.sh.year.domain.goal.goal.biggoal.domain.repository;


import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;

import java.util.Optional;

public interface BigGoalQueryRepository {


    // 큰목표 기본 조회
    Optional<BigGoal> getBigGoalInfo(Long bigGoalId);

    // 작은목표 기본 조회
    Optional<SmallGoal> getSmallGoalInfo(Long smallGoalId);

}
