package com.sh.year.domain.goal.goal.delayGoal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.goal.common.CompleteStatus;
import com.sh.year.domain.goal.goal.delayGoal.domain.QDelayGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import static com.sh.year.domain.goal.goal.delayGoal.domain.QDelayGoal.delayGoal;

@Repository
@RequiredArgsConstructor
public class DelayGoalQueryRepositoryImpl implements DelayGoalQueryRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public void bulkUpdateAboutStatusDelayToFail(LocalDate yesterday) {
        queryFactory.update(delayGoal)
                .set(delayGoal.completeStatus, CompleteStatus.FAIL)
                .where(delayGoal.endDate.eq(yesterday),
                        delayGoal.completeStatus.eq(CompleteStatus.DELAY))
                .execute();
    }
}
