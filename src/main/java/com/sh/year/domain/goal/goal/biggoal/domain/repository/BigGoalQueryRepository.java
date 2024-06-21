package com.sh.year.domain.goal.goal.biggoal.domain.repository;


import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;

import java.util.List;
import java.util.Optional;

public interface BigGoalQueryRepository {


    // 큰목표 기본 조회
    Optional<BigGoal> getBigGoalInfoByBigGoalId(Long bigGoalId);

    List<BigGoal> getBigGoalListByUserId(Long userId);


}
