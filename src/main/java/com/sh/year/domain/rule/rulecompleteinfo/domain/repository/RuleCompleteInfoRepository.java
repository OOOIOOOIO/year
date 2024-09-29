package com.sh.year.domain.rule.rulecompleteinfo.domain.repository;

import com.sh.year.domain.rule.rule.domain.Rule;
import com.sh.year.domain.rule.rulecompleteinfo.domain.RuleCompleteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RuleCompleteInfoRepository extends JpaRepository<RuleCompleteInfo, Long> {

    List<RuleCompleteInfo> findAllByRule(Rule rule);
    Optional<RuleCompleteInfo> findByYearAndMonthAndRule(int year, int month, Rule rule);


}
