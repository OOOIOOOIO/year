package com.sh.year.domain.user.application;


import com.sh.year.domain.user.domain.repository.UsersQueryRepositoryImpl;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UsersService {


    private final UsersRepository usersRepository;
    private final UsersQueryRepositoryImpl usersQueryRepository;


    /**
     * 회원 정보 수정
     */

    /**
     * 회원 조회
     */

    /**
     * 회원 삭제
     */






    /**
     * 회원 조회 - 중복 방지
     */
//    private boolean isDuplicateUsername(String username){
//
//        return !usersRepository.findByUsername(username).isPresent();
//    }

    /**
     * 닉네임 수정
     */
//    public void updateNickname(long userId, String nickname){
//        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));
//
//        users.changeNickname(nickname);
//    }


}
