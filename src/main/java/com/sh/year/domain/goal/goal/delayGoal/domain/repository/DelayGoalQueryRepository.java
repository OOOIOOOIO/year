package com.sh.year.domain.goal.goal.delayGoal.domain.repository;

import java.time.LocalDate;

public interface DelayGoalQueryRepository {

    void bulkUpdateAboutStatusDelayToFail(LocalDate localDate);
}
