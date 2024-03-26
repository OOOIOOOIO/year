package com.sh.year.global.oauth;

import com.sh.year.domain.user.domain.Role;
import com.sh.year.domain.user.domain.Users;
import com.sh.year.domain.user.domain.repository.UsersQueryRepositoryImpl;
import com.sh.year.domain.user.domain.repository.UsersRepository;
import com.sh.year.global.oauth.dto.OAuthAttributes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsersRepository userRepository;
    private final UsersQueryRepositoryImpl usersQueryRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 진행중인 서비스를 구분하기 위해 문자열로 받음. oAuth2UserRequest.getClientRegistration().getRegistrationId()에 값이 들어있다. {registrationId='naver'} 이런식
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("=================== registrationId : " + registrationId + " ===================");

        // OAuth2 로그인 시 키 값이 된다. 구글은 키 값이 "sub"이고, 네이버는 "response"이고, 카카오는 "id"이다. 각각 다르므로 이렇게 따로 변수로 받아서 넣어줘야함.
        String userNameAttributeName = userRequest.getClientRegistration()
                                                    .getProviderDetails()
                                                    .getUserInfoEndpoint()
                                                    .getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes()); // 소셜러그인 결과값


        // 유저 저장
        Users user = saveOrUpdate(attributes);

        attributes.setUserId(user.getUserId());

        Map<String, Object> userInfoMap = attributes.convertToMap();


        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getKey())),
                userInfoMap,
                attributes.getNameAttributeKey()
        );
    }



    // 혹시 이미 저장된 정보라면, update 처리
    private Users saveOrUpdate(OAuthAttributes attributes){
//        Users user = usersQueryRepository.findByEmailAndProvider(attributes.getEmail(), attributes.getProvider())
//                .map(entity -> entity.update(attributes.getEmail(), attributes.getPicture(), attributes.getProvider()))
//                .orElse(attributes.createUser());

        Optional<Users> usersOptional = usersQueryRepository.findByEmailAndProvider(attributes.getEmail(), attributes.getProvider());

        if(usersOptional.isPresent()){
            Users users = usersOptional.get();
            users.update(attributes.getEmail(), attributes.getPicture(), attributes.getProvider());

            return users;
        }

        return userRepository.save(Users.createUser(attributes.getEmail(), attributes.getPicture(), attributes.getProvider(), Role.USER));

    }


}
