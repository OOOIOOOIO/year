package com.sh.year.domain.goal.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class GoalQueryRepositoryImpl implements GoalQueryRepository{

    private final JPAQueryFactory queryFactory;


}
