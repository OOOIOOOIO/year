package com.sh.year.domain.goal.review.smallgoalreview.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;



@Repository
@RequiredArgsConstructor
public class SmallGoalReviewQueryRepositoryImpl implements SmallGoalReviewQueryRepository {

    private final JPAQueryFactory queryFactory;

}
