package com.sh.year.domain.goal.goal.biggoal.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBigGoal is a Querydsl query type for BigGoal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBigGoal extends EntityPathBase<BigGoal> {

    private static final long serialVersionUID = 1026986185L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBigGoal bigGoal = new QBigGoal("bigGoal");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final NumberPath<Long> bigGoalId = createNumber("bigGoalId", Long.class);

    public final com.sh.year.domain.goal.review.biggoalreview.domain.QBigGoalReview bigGoalReview;

    public final StringPath category = createString("category");

    public final EnumPath<com.sh.year.domain.goal.goal.common.CompleteStatus> completeStatus = createEnum("completeStatus", com.sh.year.domain.goal.goal.common.CompleteStatus.class);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final EnumPath<ShareStatus> shareStatus = createEnum("shareStatus", ShareStatus.class);

    public final ListPath<com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal, com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal> smallGoalList = this.<com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal, com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal>createList("smallGoalList", com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal.class, com.sh.year.domain.goal.goal.smallgoal.domain.QSmallGoal.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.year.domain.user.domain.QUsers users;

    public QBigGoal(String variable) {
        this(BigGoal.class, forVariable(variable), INITS);
    }

    public QBigGoal(Path<? extends BigGoal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBigGoal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBigGoal(PathMetadata metadata, PathInits inits) {
        this(BigGoal.class, metadata, inits);
    }

    public QBigGoal(Class<? extends BigGoal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bigGoalReview = inits.isInitialized("bigGoalReview") ? new com.sh.year.domain.goal.review.biggoalreview.domain.QBigGoalReview(forProperty("bigGoalReview"), inits.get("bigGoalReview")) : null;
        this.users = inits.isInitialized("users") ? new com.sh.year.domain.user.domain.QUsers(forProperty("users")) : null;
    }

}

