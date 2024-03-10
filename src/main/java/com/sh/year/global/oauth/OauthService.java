package com.sh.year.global.oauth;


import com.sh.year.api.oauth.dto.req.KakaoAccessTokenReqDto;
import com.sh.year.api.oauth.dto.req.KakaoAuthCodeReqDto;
import com.sh.year.api.oauth.dto.res.KakaoAccessTokenResDto;
import com.sh.year.api.oauth.dto.res.KakaoAccountInfoResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OauthService {

    private final Oauth2ApiClient oauth2ApiClient;
    private final static String RESPONSE_TYPE ="code";
    private static final String GRANT_TYPE = "authorization_code"; // authorization_code로 고정
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.client-secret}")
    private String clientSecret;
    @Value("${kakao.redirect-uri}")
    private String redirect_uri;


    /**
     * kakao AuthCode 발급
     */
    public KakaoAuthCodeReqDto makeAuthCodeDto() {

        return KakaoAuthCodeReqDto.builder()
                .redirect_uri(redirect_uri)
                .client_id(clientId)
                .response_type(RESPONSE_TYPE)
                .build();
    }




    /**
     * kakao Access_token 발급
     */
    public KakaoAccessTokenResDto getAccessToken(String code) {

        KakaoAccessTokenReqDto kakaoAccessTokenReqDto = KakaoAccessTokenReqDto.builder()
                .grant_type(GRANT_TYPE)
                .client_id(clientId)
                .redirect_uri(redirect_uri)
                .code(code)
                .client_secret(clientSecret)
                .build();


        // token
        KakaoAccessTokenResDto token = oauth2ApiClient.getToken(kakaoAccessTokenReqDto);

        return token;

    }

    /**
     * kakao user info 조회
     */
    public KakaoAccountInfoResDto getAccountInfo(KakaoAccessTokenResDto token) {
        // userInfo 가져오기
        KakaoAccountInfoResDto accountInfo = oauth2ApiClient.getAccountInfo(token.getAccess_token());

        return accountInfo;

    }
}
