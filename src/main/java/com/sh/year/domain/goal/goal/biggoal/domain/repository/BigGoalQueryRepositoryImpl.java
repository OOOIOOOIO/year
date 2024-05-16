package com.sh.year.domain.goal.goal.biggoal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.goal.biggoal.domain.BigGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.year.domain.goal.goal.domain.QBigGoal.bigGoal;
import static com.sh.year.domain.goal.goal.domain.QSmallGoal.*;


@Repository
@RequiredArgsConstructor
public class BigGoalQueryRepositoryImpl implements BigGoalQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<BigGoal> getBigGoalInfo(Long bigGoalId) {


        return Optional.ofNullable(
                queryFactory.select(bigGoal)
                .from(bigGoal)
                .join(bigGoal.rule).fetchJoin()
                .where(bigGoal.bigGoalId.eq(bigGoalId))
                .fetchOne());


    }


    @Override
    public Optional<SmallGoal> getSmallGoalInfo(Long smallGoalId) {

        return Optional.ofNullable(
                queryFactory.select(smallGoal)
                .from(smallGoal)
                .join(smallGoal.rule).fetchJoin().join(smallGoal.rule.repeatDatesList).fetchJoin()
                .where(smallGoal.smallGoalId.eq(smallGoalId))
                .fetchOne());


    }


}
