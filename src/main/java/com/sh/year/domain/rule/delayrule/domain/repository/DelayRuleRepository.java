package com.sh.year.domain.rule.delayrule.domain.repository;

import com.sh.year.domain.rule.delayrule.domain.DelayRule;
import com.sh.year.domain.rule.rule.domain.Rule;
import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelayRuleRepository extends JpaRepository<DelayRule, Long> {
    List<DelayRule> findAllByUsers(Users users);


    @Query(value = "select d " +
            "from DelayRule d " +
            "where d.users.userId = :userId and d.completeStatus not in(com.sh.year.domain.common.CompleteStatus.COMP)")
    List<DelayRule> findAllByUsersAndCompleteStatus(@Param(value = "userId") Long userId);


    @Query(value = "select d " +
            "from DelayRule d " +
            "where d.completeStatus = com.sh.year.domain.common.CompleteStatus.DELAY")
    List<DelayRule> findAllOfDelayStatusByCompleteStatus();


    Optional<DelayRule> findByRule(Rule rule);
}
