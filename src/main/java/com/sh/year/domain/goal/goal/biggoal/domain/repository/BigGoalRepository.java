package com.sh.year.domain.goal.goal.biggoal.domain.repository;

import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BigGoalRepository extends JpaRepository<BigGoal, Long> {
    List<BigGoal> findAllByUsers(Users users);


    /*
    native쿼리 작성하기
   List<dto>로 프로젝션
select distinct *
from big_goal as bg
join small_goal as sg join big_goal b on b.big_goal_id = sg.big_goal_id
join rule as r on sg.small_goal_id = r.small_goal_id
join rule_alert_info rai on r.rule_id = rai.rule_id
where rai.year = 2024 and rai.month = 6;
and rai.alert_day IN (0x0001000001000001010000010000010100000100000101000001000001010000);

     */
}
