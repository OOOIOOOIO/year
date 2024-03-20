package com.sh.year.domain.goal.rule.domain.repository;

import com.sh.year.domain.goal.rule.domain.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Long> {
}
