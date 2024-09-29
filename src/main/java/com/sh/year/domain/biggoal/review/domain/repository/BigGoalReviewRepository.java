package com.sh.year.domain.biggoal.review.domain.repository;

import com.sh.year.domain.biggoal.biggoal.domain.BigGoal;
import com.sh.year.domain.biggoal.review.domain.BigGoalReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BigGoalReviewRepository extends JpaRepository<BigGoalReview, Long> {

    Optional<BigGoalReview> findByBigGoal_BigGoalId(Long bigGoalId);


}
