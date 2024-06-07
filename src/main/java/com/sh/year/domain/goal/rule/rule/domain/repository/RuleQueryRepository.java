package com.sh.year.domain.goal.rule.rule.domain.repository;

import com.sh.year.domain.goal.rule.rule.domain.Rule;

import java.util.Optional;

public interface RuleQueryRepository {

    Optional<Rule> findRuleAndRuleCompleteInfo(int year, int month, Long ruleId);
}
