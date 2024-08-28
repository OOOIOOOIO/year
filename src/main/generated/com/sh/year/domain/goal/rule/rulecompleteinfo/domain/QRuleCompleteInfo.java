package com.sh.year.domain.goal.rule.rulecompleteinfo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRuleCompleteInfo is a Querydsl query type for RuleCompleteInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRuleCompleteInfo extends EntityPathBase<RuleCompleteInfo> {

    private static final long serialVersionUID = 751666110L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRuleCompleteInfo ruleCompleteInfo = new QRuleCompleteInfo("ruleCompleteInfo");

    public final ArrayPath<byte[], Byte> completeDay = createArray("completeDay", byte[].class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final NumberPath<Long> repeatId = createNumber("repeatId", Long.class);

    public final com.sh.year.domain.goal.rule.rule.domain.QRule rule;

    public final NumberPath<Integer> totalDayCnt = createNumber("totalDayCnt", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QRuleCompleteInfo(String variable) {
        this(RuleCompleteInfo.class, forVariable(variable), INITS);
    }

    public QRuleCompleteInfo(Path<? extends RuleCompleteInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRuleCompleteInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRuleCompleteInfo(PathMetadata metadata, PathInits inits) {
        this(RuleCompleteInfo.class, metadata, inits);
    }

    public QRuleCompleteInfo(Class<? extends RuleCompleteInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.goal.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
    }

}

