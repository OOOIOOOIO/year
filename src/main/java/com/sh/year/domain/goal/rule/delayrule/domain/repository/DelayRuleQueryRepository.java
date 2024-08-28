package com.sh.year.domain.goal.rule.delayrule.domain.repository;

import java.time.LocalDate;

public interface DelayRuleQueryRepository {

    void bulkUpdateAboutStatusDelayToFail(LocalDate localDate);
}
