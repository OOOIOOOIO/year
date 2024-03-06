package com.sh.year.domain.user.domain.repository;

import com.sh.year.domain.user.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
