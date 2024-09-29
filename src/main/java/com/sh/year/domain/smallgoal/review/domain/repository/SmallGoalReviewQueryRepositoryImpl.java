package com.sh.year.domain.smallgoal.review.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class SmallGoalReviewQueryRepositoryImpl implements SmallGoalReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

}
