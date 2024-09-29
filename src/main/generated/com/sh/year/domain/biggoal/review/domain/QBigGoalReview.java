package com.sh.year.domain.biggoal.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBigGoalReview is a Querydsl query type for BigGoalReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBigGoalReview extends EntityPathBase<BigGoalReview> {

    private static final long serialVersionUID = 1599033655L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBigGoalReview bigGoalReview = new QBigGoalReview("bigGoalReview");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final com.sh.year.domain.biggoal.biggoal.domain.QBigGoal bigGoal;

    public final NumberPath<Long> bigGoalReviewId = createNumber("bigGoalReviewId", Long.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Integer> starRating = createNumber("starRating", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBigGoalReview(String variable) {
        this(BigGoalReview.class, forVariable(variable), INITS);
    }

    public QBigGoalReview(Path<? extends BigGoalReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBigGoalReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBigGoalReview(PathMetadata metadata, PathInits inits) {
        this(BigGoalReview.class, metadata, inits);
    }

    public QBigGoalReview(Class<? extends BigGoalReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bigGoal = inits.isInitialized("bigGoal") ? new com.sh.year.domain.biggoal.biggoal.domain.QBigGoal(forProperty("bigGoal"), inits.get("bigGoal")) : null;
    }

}

