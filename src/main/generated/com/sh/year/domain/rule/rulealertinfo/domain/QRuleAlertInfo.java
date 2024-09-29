package com.sh.year.domain.rule.rulealertinfo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRuleAlertInfo is a Querydsl query type for RuleAlertInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRuleAlertInfo extends EntityPathBase<RuleAlertInfo> {

    private static final long serialVersionUID = 502663921L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRuleAlertInfo ruleAlertInfo = new QRuleAlertInfo("ruleAlertInfo");

    public final ArrayPath<byte[], Byte> alertDay = createArray("alertDay", byte[].class);

    public final NumberPath<Long> alertId = createNumber("alertId", Long.class);

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final com.sh.year.domain.rule.rule.domain.QRule rule;

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QRuleAlertInfo(String variable) {
        this(RuleAlertInfo.class, forVariable(variable), INITS);
    }

    public QRuleAlertInfo(Path<? extends RuleAlertInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRuleAlertInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRuleAlertInfo(PathMetadata metadata, PathInits inits) {
        this(RuleAlertInfo.class, metadata, inits);
    }

    public QRuleAlertInfo(Class<? extends RuleAlertInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rule = inits.isInitialized("rule") ? new com.sh.year.domain.rule.rule.domain.QRule(forProperty("rule"), inits.get("rule")) : null;
    }

}

