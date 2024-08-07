package com.sh.year.domain.goal.review.smallgoalreview.domain.repository;

import com.sh.year.domain.goal.goal.smallgoal.domain.SmallGoal;
import com.sh.year.domain.goal.review.smallgoalreview.domain.SmallGoalReview;
import com.sh.year.domain.goal.rule.rule.domain.Rule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmallGoalReviewRepository extends JpaRepository<SmallGoalReview, Long> {

    List<SmallGoalReview> findAllByRule(Rule rule, Pageable pageable);
}
