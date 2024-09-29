package com.sh.year.domain.user.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsers is a Querydsl query type for Users
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUsers extends EntityPathBase<Users> {

    private static final long serialVersionUID = -1398127524L;

    public static final QUsers users = new QUsers("users");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final ListPath<com.sh.year.domain.biggoal.biggoal.domain.BigGoal, com.sh.year.domain.biggoal.biggoal.domain.QBigGoal> bigGoalList = this.<com.sh.year.domain.biggoal.biggoal.domain.BigGoal, com.sh.year.domain.biggoal.biggoal.domain.QBigGoal>createList("bigGoalList", com.sh.year.domain.biggoal.biggoal.domain.BigGoal.class, com.sh.year.domain.biggoal.biggoal.domain.QBigGoal.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.sh.year.domain.rule.delayrule.domain.DelayRule, com.sh.year.domain.rule.delayrule.domain.QDelayRule> delayRuleList = this.<com.sh.year.domain.rule.delayrule.domain.DelayRule, com.sh.year.domain.rule.delayrule.domain.QDelayRule>createList("delayRuleList", com.sh.year.domain.rule.delayrule.domain.DelayRule.class, com.sh.year.domain.rule.delayrule.domain.QDelayRule.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath instagramAccount = createString("instagramAccount");

    public final StringPath nickname = createString("nickname");

    public final StringPath profileImg = createString("profileImg");

    public final StringPath provider = createString("provider");

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final StringPath stateMessage = createString("stateMessage");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QUsers(String variable) {
        super(Users.class, forVariable(variable));
    }

    public QUsers(Path<? extends Users> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsers(PathMetadata metadata) {
        super(Users.class, metadata);
    }

}

