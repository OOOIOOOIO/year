package com.sh.year.domain.goal.rule.domain.repository;

import com.sh.year.domain.goal.rule.domain.Rule;
import com.sh.year.domain.goal.rule.domain.RuleRepeatDates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuleRepeatDatesRepository extends JpaRepository<RuleRepeatDates, Long> {

    List<RuleRepeatDates> findAllByRule(Rule rule);

}
