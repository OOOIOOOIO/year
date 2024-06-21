package com.sh.year.domain.user.domain.repository;

import com.sh.year.domain.user.domain.Users;

import java.util.List;
import java.util.Optional;

public interface UsersQueryRepository {


    Optional<Users> findByEmailAndProvider(String email, String provider);



}
