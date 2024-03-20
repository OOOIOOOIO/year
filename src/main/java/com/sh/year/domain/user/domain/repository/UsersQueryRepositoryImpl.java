package com.sh.year.domain.user.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sh.year.domain.user.domain.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sh.year.domain.user.domain.QUsers.users;

@Repository
@RequiredArgsConstructor
public class UsersQueryRepositoryImpl implements UsersQueryRepository{

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<Users> findByEmailAndProvider(String email, String provider) {

        return Optional.ofNullable(
                queryFactory
                        .select(users)
                        .from(users)
                        .where(users.email.eq(email), users.provider.eq(provider))
                        .fetchOne()
        );
    }


}
