package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.sh.year.api.main.controller.dto.res.TodayAlertSmallGoalInterface;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmallGoalRepository extends JpaRepository<SmallGoal, Long> {


    /*
select *
from small_goal as sg
join rule as r on sg.small_goal_id = r.small_goal_id
join rule_alert_info rai on r.rule_id = rai.rule_id
where rai.year = 2024 and rai.month = 6
and rai.alert_day IN (0x0001000001000001010000010000010100000100000101000001000001010000);

     */

    @Query(value = "select sg.small_goal_id as smallGoalId, sg.title as title, sg.icon as icon, r.rule_id as ruleId, r.routine as routine, r.contents as contents, r.time_at as timeAt, rai.alert_day as alertDay " +
    "from small_goal sg " +
    "join rule as r on sg.small_goal_id = r.small_goal_id " +
    "join rule_alert_info rai on r.rule_id = rai.rule_id " +
    "where rai.year = :year and rai.month = :month", nativeQuery = true)
    List<TodayAlertSmallGoalInterface> getTodayAlertGoalList(@Param("year") int year,
                                                             @Param("month") int month);
}

