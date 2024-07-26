package com.sh.year.domain.user.application;


import com.sh.year.api.kakao.api.dto.KakaoUserInfoResDto;
import com.sh.year.domain.user.api.dto.UserInfoUpdateReqDto;
import com.sh.year.domain.user.domain.Role;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersQueryRepositoryImpl;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final UsersQueryRepositoryImpl usersQueryRepository;


    /**
     * 회원 저장
     */
    public Long saveUserInfo(KakaoUserInfoResDto kakaoUserInfoResDto) {
        Users user = Users.createUser(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider(), Role.USER);

        Users savedUser = usersRepository.save(user);

        return savedUser.getUserId();
    }

    /**
     * 회원 정보 수정
     */
    public void updateUserInfo(Long userId, UserInfoUpdateReqDto userInfoUpdateReqDto){
        Users users = usersRepository.findById(userId).orElseThrow(() -> new CustomException(CustomErrorCode.UserNotFoundException));

        users.updateUserInfo(userInfoUpdateReqDto);

    }

    public int isUserExist(UserInfoFromHeaderDto userInfoFromHeaderDto) {
        if(usersQueryRepository.findByEmailAndProvider(userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider()).isEmpty()){
            return 0;
        }

        return 1;
    }

    public Long isUserExist(KakaoUserInfoResDto kakaoUserInfoResDto) {
        Optional<Users> user = usersQueryRepository.findByEmailAndProvider(kakaoUserInfoResDto.getEmail(), kakaoUserInfoResDto.getProvider());

        if(!user.isEmpty()){
            return user.get().getUserId();
        }

        return -1L;
    }




}
