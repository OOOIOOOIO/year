package com.sh.year.domain.goal.review.smallgoalreview.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSmallGoalReview is a Querydsl query type for SmallGoalReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmallGoalReview extends EntityPathBase<SmallGoalReview> {

    private static final long serialVersionUID = 653115556L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSmallGoalReview smallGoalReview = new QSmallGoalReview("smallGoalReview");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final EnumPath<com.sh.year.domain.goal.goal.common.CompleteStatus> completeStatus = createEnum("completeStatus", com.sh.year.domain.goal.goal.common.CompleteStatus.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.sh.year.domain.goal.rule.rule.domain.QRule rule;

    public final NumberPath<Long> smallGoalReviewId = createNumber("smallGoalReviewId", Long.class);

    public final NumberPath<Integer> starRating = createNumber("starRating", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSmallGoalReview(String variable) {
        this(SmallGoalReview.class, forVariable(variable), INITS);
    }

    public QSmallGoalReview(Path<? extends SmallGoalReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSmallGoalReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSmallGoalReview(PathMetadata metadata, PathInits inits) {
        this(SmallGoalReview.class, metadata, inits);
    }

    public QSmallGoalReview(Class<? extends SmallGoalReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.goal.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
    }

}

