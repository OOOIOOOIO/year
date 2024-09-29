package com.sh.year.domain.rule.rule.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRule is a Querydsl query type for Rule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRule extends EntityPathBase<Rule> {

    private static final long serialVersionUID = 1937359905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRule rule = new QRule("rule");

    public final com.sh.year.domain.common.QBaseTimeEntity _super = new com.sh.year.domain.common.QBaseTimeEntity(this);

    public final StringPath contents = createString("contents");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final ListPath<com.sh.year.domain.rule.delayrule.domain.DelayRule, com.sh.year.domain.rule.delayrule.domain.QDelayRule> delayRuleList = this.<com.sh.year.domain.rule.delayrule.domain.DelayRule, com.sh.year.domain.rule.delayrule.domain.QDelayRule>createList("delayRuleList", com.sh.year.domain.rule.delayrule.domain.DelayRule.class, com.sh.year.domain.rule.delayrule.domain.QDelayRule.class, PathInits.DIRECT2);

    public final NumberPath<Integer> routine = createNumber("routine", Integer.class);

    public final ListPath<com.sh.year.domain.rule.rulealertinfo.domain.RuleAlertInfo, com.sh.year.domain.rule.rulealertinfo.domain.QRuleAlertInfo> ruleAlertInfoList = this.<com.sh.year.domain.rule.rulealertinfo.domain.RuleAlertInfo, com.sh.year.domain.rule.rulealertinfo.domain.QRuleAlertInfo>createList("ruleAlertInfoList", com.sh.year.domain.rule.rulealertinfo.domain.RuleAlertInfo.class, com.sh.year.domain.rule.rulealertinfo.domain.QRuleAlertInfo.class, PathInits.DIRECT2);

    public final ListPath<com.sh.year.domain.rule.rulecompleteinfo.domain.RuleCompleteInfo, com.sh.year.domain.rule.rulecompleteinfo.domain.QRuleCompleteInfo> ruleCompleteInfoList = this.<com.sh.year.domain.rule.rulecompleteinfo.domain.RuleCompleteInfo, com.sh.year.domain.rule.rulecompleteinfo.domain.QRuleCompleteInfo>createList("ruleCompleteInfoList", com.sh.year.domain.rule.rulecompleteinfo.domain.RuleCompleteInfo.class, com.sh.year.domain.rule.rulecompleteinfo.domain.QRuleCompleteInfo.class, PathInits.DIRECT2);

    public final NumberPath<Long> ruleId = createNumber("ruleId", Long.class);

    public final ListPath<com.sh.year.domain.rule.rulerepeatday.domain.RuleRepeatDay, com.sh.year.domain.rule.rulerepeatday.domain.QRuleRepeatDay> ruleRepeatDayList = this.<com.sh.year.domain.rule.rulerepeatday.domain.RuleRepeatDay, com.sh.year.domain.rule.rulerepeatday.domain.QRuleRepeatDay>createList("ruleRepeatDayList", com.sh.year.domain.rule.rulerepeatday.domain.RuleRepeatDay.class, com.sh.year.domain.rule.rulerepeatday.domain.QRuleRepeatDay.class, PathInits.DIRECT2);

    public final com.sh.year.domain.smallgoal.smallgoal.domain.QSmallGoal smallGoal;

    public final ListPath<com.sh.year.domain.smallgoal.review.domain.SmallGoalReview, com.sh.year.domain.smallgoal.review.domain.QSmallGoalReview> smallGoalReviewList = this.<com.sh.year.domain.smallgoal.review.domain.SmallGoalReview, com.sh.year.domain.smallgoal.review.domain.QSmallGoalReview>createList("smallGoalReviewList", com.sh.year.domain.smallgoal.review.domain.SmallGoalReview.class, com.sh.year.domain.smallgoal.review.domain.QSmallGoalReview.class, PathInits.DIRECT2);

    public final TimePath<java.time.LocalTime> timeAt = createTime("timeAt", java.time.LocalTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QRule(String variable) {
        this(Rule.class, forVariable(variable), INITS);
    }

    public QRule(Path<? extends Rule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRule(PathMetadata metadata, PathInits inits) {
        this(Rule.class, metadata, inits);
    }

    public QRule(Class<? extends Rule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.smallGoal = inits.isInitialized("smallGoal") ? new com.sh.year.domain.smallgoal.smallgoal.domain.QSmallGoal(forProperty("smallGoal"), inits.get("smallGoal")) : null;
    }

}

