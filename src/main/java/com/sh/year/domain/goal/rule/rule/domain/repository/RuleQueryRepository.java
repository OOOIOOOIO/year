package com.sh.year.domain.goal.rule.rule.domain.repository;

import com.sh.year.domain.goal.rule.rule.domain.Rule;

import java.util.Optional;

public interface RuleQueryRepository {

    Optional<Rule> findRuleCompleteInfoUsingYearAndMonth(int year, int month, Long ruleId);

    Optional<Rule> findRuleAlertInfoUsingYearAndMonth(int year, int month, Long ruleId);
}
