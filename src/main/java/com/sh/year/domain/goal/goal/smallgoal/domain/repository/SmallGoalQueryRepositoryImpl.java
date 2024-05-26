package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal.smallGoal;

@Repository
@RequiredArgsConstructor
public class SmallGoalQueryRepositoryImpl implements SmallGoalQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<SmallGoal> findSmallGoalById(long smallGoalId) {

        return Optional.ofNullable(
                queryFactory.select(smallGoal)
                        .from(smallGoal)
                        .join(smallGoal.rule).fetchJoin()
//                        .join(smallGoal.rule.ruleCompleteInfoList).fetchJoin()
                        .join(smallGoal.rule.ruleRepeatDayList).fetchJoin() // 이거 나눠서 가져와야하겠네
                        .where(smallGoal.smallGoalId.eq(smallGoalId))
                        .fetchOne());
    }

}
