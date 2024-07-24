package com.sh.year.global.security;


import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersQueryRepositoryImpl;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Getter
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService {

    private final UsersRepository userRepository;
    private final UsersQueryRepositoryImpl usersQueryRepository;

    @Transactional
    public UserDetails loadUserByUsernameAndProvider(String email, String provider) throws UsernameNotFoundException {
        Users user = usersQueryRepository.findByEmailAndProvider(email, provider)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return UserDetailsImpl.build(user);
    }

}
