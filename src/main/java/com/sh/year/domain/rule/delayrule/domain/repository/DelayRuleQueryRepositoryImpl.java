package com.sh.year.domain.rule.delayrule.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.common.CompleteStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.sh.year.domain.rule.delayrule.domain.QDelayRule.delayRule;


@Repository
@RequiredArgsConstructor
public class DelayRuleQueryRepositoryImpl implements DelayRuleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void bulkUpdateAboutStatusDelayToFail(LocalDate yesterday) {
        queryFactory.update(delayRule)
                .set(delayRule.completeStatus, CompleteStatus.FAIL)
                .where(delayRule.endDate.eq(yesterday),
                        delayRule.completeStatus.eq(CompleteStatus.DELAY))
                .execute();
    }
}
