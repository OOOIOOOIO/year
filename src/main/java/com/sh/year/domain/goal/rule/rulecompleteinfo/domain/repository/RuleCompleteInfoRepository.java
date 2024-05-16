package com.sh.year.domain.goal.rule.rulecompleteinfo.domain.repository;

import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RuleCompleteInfoRepository extends JpaRepository<RuleCompleteInfo, Long> {

    List<RuleCompleteInfo> findAllByRule(Rule rule);

}
