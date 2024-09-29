package com.sh.year.domain.rule.rulerepeatday.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRuleRepeatDay is a Querydsl query type for RuleRepeatDay
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRuleRepeatDay extends EntityPathBase<RuleRepeatDay> {

    private static final long serialVersionUID = 694117137L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRuleRepeatDay ruleRepeatDay = new QRuleRepeatDay("ruleRepeatDay");

    public final NumberPath<Integer> day = createNumber("day", Integer.class);

    public final com.sh.year.domain.rule.rule.domain.QRule rule;

    public final NumberPath<Long> ruleRepeatDayId = createNumber("ruleRepeatDayId", Long.class);

    public QRuleRepeatDay(String variable) {
        this(RuleRepeatDay.class, forVariable(variable), INITS);
    }

    public QRuleRepeatDay(Path<? extends RuleRepeatDay> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRuleRepeatDay(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRuleRepeatDay(PathMetadata metadata, PathInits inits) {
        this(RuleRepeatDay.class, metadata, inits);
    }

    public QRuleRepeatDay(Class<? extends RuleRepeatDay> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
    }

}

