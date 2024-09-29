package com.sh.year.domain.smallgoal.review.domain.repository;

import com.sh.year.domain.smallgoal.review.domain.SmallGoalReview;
import com.sh.year.domain.rule.rule.domain.Rule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmallGoalReviewRepository extends JpaRepository<SmallGoalReview, Long> {

    List<SmallGoalReview> findAllByRule(Rule rule, Pageable pageable);
}
