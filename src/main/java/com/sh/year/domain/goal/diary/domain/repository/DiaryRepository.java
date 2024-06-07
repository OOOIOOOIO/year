package com.sh.year.domain.goal.diary.domain.repository;

import com.sh.year.domain.goal.diary.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<Diary, Long> {
}
