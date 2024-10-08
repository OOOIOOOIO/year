package com.sh.year.domain.biggoal.biggoal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sh.year.domain.biggoal.biggoal.domain.QBigGoal.bigGoal;


@Repository
@RequiredArgsConstructor
public class BigGoalQueryRepositoryImpl implements BigGoalQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<BigGoal> getBigGoalInfoByBigGoalId(Long bigGoalId) {

        return Optional.ofNullable(
                queryFactory.select(bigGoal)
                    .from(bigGoal)
                    .leftJoin(bigGoal.smallGoalList).fetchJoin()
                    .where(bigGoal.bigGoalId.eq(bigGoalId))
                    .fetchOne());


    }

    @Override
    public List<BigGoal> getBigGoalListByUserId(Long userId) {

        return queryFactory
                .select(bigGoal)
                .from(bigGoal)
                .leftJoin(bigGoal.smallGoalList).fetchJoin()
                .where(bigGoal.users.userId.eq(userId))
                .fetch();
    }





}
