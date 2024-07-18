package com.sh.year.domain.goal.rule.rule.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.rule.rule.domain.QRule;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import com.sh.year.domain.goal.rule.rulecompleteinfo.domain.QRuleCompleteInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.year.domain.goal.rule.rule.domain.QRule.rule;
import static com.sh.year.domain.goal.rule.rulealertinfo.domain.QRuleAlertInfo.ruleAlertInfo;
import static com.sh.year.domain.goal.rule.rulecompleteinfo.domain.QRuleCompleteInfo.*;

@Repository
@RequiredArgsConstructor
public class RuleQueryRepositoryImpl implements RuleQueryRepository{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<Rule> findRuleCompleteInfoUsingYearAndMonth(int year, int month, Long ruleId) {
        return Optional.ofNullable(queryFactory
                .select(rule)
                .from(rule, rule)
                .join(rule.ruleCompleteInfoList, ruleCompleteInfo).fetchJoin()
                .where(ruleCompleteInfo.year.eq(year),
                        ruleCompleteInfo.month.eq(month),
                        ruleCompleteInfo.rule.ruleId.eq(ruleId))
                .fetchOne());


    }

    @Override
    public Optional<Rule> findRuleAlertInfoUsingYearAndMonth(int year, int month, Long ruleId) {

        return Optional.ofNullable(queryFactory
                .select(rule)
                .from(rule, rule)
                .join(rule.ruleAlertInfoList, ruleAlertInfo).fetchJoin()
                .where(ruleAlertInfo.year.eq(year),
                        ruleAlertInfo.month.eq(month),
                        ruleAlertInfo.rule.ruleId.eq(ruleId))
                .fetchOne());
    }
}
