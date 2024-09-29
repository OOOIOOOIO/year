package com.sh.year.domain.rule.delayrule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelayRule is a Querydsl query type for DelayRule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelayRule extends EntityPathBase<DelayRule> {

    private static final long serialVersionUID = -1065528527L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDelayRule delayRule = new QDelayRule("delayRule");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final EnumPath<com.sh.year.domain.common.CompleteStatus> completeStatus = createEnum("completeStatus", com.sh.year.domain.common.CompleteStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> delayRuleId = createNumber("delayRuleId", Long.class);

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final com.sh.year.domain.rule.rule.domain.QRule rule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.sh.year.domain.user.domain.QUsers users;

    public QDelayRule(String variable) {
        this(DelayRule.class, forVariable(variable), INITS);
    }

    public QDelayRule(Path<? extends DelayRule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDelayRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDelayRule(PathMetadata metadata, PathInits inits) {
        this(DelayRule.class, metadata, inits);
    }

    public QDelayRule(Class<? extends DelayRule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
        this.users = inits.isInitialized("users") ? new com.sh.year.domain.user.domain.QUsers(forProperty("users")) : null;
    }

}

