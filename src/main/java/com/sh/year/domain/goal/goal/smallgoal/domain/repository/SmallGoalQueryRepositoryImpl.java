package com.sh.year.domain.goal.goal.smallgoal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal;
import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal.smallGoal;

@Repository
@RequiredArgsConstructor
public class SmallGoalQueryRepositoryImpl implements SmallGoalQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<SmallGoal> findSmallGoalBySmallGoalId(Long smallGoalId) {

        return Optional.ofNullable(
                queryFactory
                        .select(smallGoal)
                        .from(smallGoal)
                        .join(smallGoal.rule).fetchJoin()
                        .where(smallGoal.smallGoalId.eq(smallGoalId))
                        .fetchOne());
    }


    @Override
    public List<SmallGoal> findSmallGoalListByBigGoalId(Long bigGoalId) {

        return queryFactory
                .select(smallGoal)
                .from(smallGoal)
                .join(smallGoal.rule).fetchJoin()
                .where(smallGoal.bigGoal.bigGoalId.eq(bigGoalId))
                .fetch();
    }


}


/**
 * 연관관계시 자식타입에 OneTOMany(List) 쪽에 fk 있는쪽에 JsonIgnore 걸기
 */
