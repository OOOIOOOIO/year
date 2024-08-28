package com.sh.year.domain.goal.rule.delayrule.domain.repository;

import com.sh.year.domain.goal.rule.delayrule.domain.DelayRule;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DelayRuleRepository extends JpaRepository<DelayRule, Long> {
    List<DelayRule> findAllByUsers(Users users);


    @Query(value = "select d " +
            "from DelayRule d " +
            "where d.users.userId = :userId and d.completeStatus not in(com.sh.year.domain.goal.goal.common.CompleteStatus.COMP)")
    List<DelayRule> findAllByUsersAndCompleteStatus(Long userId);


    @Query(value = "select d " +
            "from DelayRule d " +
            "where d.completeStatus =com.sh.year.domain.goal.goal.common.CompleteStatus.DELAY")
    List<DelayRule> findAllOfDelayStatusByCompleteStatus();


    Optional<DelayRule> findByRule(Rule rule);
}
