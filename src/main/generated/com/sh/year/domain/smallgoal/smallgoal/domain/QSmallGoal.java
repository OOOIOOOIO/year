package com.sh.year.domain.smallgoal.smallgoal.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSmallGoal is a Querydsl query type for SmallGoal
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSmallGoal extends EntityPathBase<SmallGoal> {

    private static final long serialVersionUID = -1824466467L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSmallGoal smallGoal = new QSmallGoal("smallGoal");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final com.sh.year.domain.biggoal.biggoal.domain.QBigGoal bigGoal;

    public final EnumPath<com.sh.year.domain.common.CompleteStatus> completeStatus = createEnum("completeStatus", com.sh.year.domain.common.CompleteStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final StringPath icon = createString("icon");

    public final com.sh.year.domain.rule.rule.domain.QRule rule;

    public final NumberPath<Long> smallGoalId = createNumber("smallGoalId", Long.class);

    public final StringPath title = createString("title");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QSmallGoal(String variable) {
        this(SmallGoal.class, forVariable(variable), INITS);
    }

    public QSmallGoal(Path<? extends SmallGoal> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSmallGoal(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSmallGoal(PathMetadata metadata, PathInits inits) {
        this(SmallGoal.class, metadata, inits);
    }

    public QSmallGoal(Class<? extends SmallGoal> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bigGoal = inits.isInitialized("bigGoal") ? new com.sh.year.domain.biggoal.biggoal.domain.QBigGoal(forProperty("bigGoal"), inits.get("bigGoal")) : null;
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
    }

}

