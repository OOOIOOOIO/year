package com.sh.year.global.oauth;


import com.sh.year.api.oauth.dto.req.KakaoAccessTokenReqDto;
import com.sh.year.api.oauth.dto.res.KakaoAccessTokenResDto;
import com.sh.year.api.oauth.dto.res.KakaoAccountInfoResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.MultiValueMap;


@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class Oauth2ApiClient {

    private final RestTemplate restTemplate;
    private static final String TOKEN_BASE_URL = "https://kauth.kakao.com";
    private static final String ACCOUNT_BASE_URL = "https://kapi.kakao.com";
    private static final String BEARER = "Bearer ";




    /**
     * kakao AuthCode 발급
     */




    /**
     * kakao Access_token 발급
     */
    public KakaoAccessTokenResDto getToken(KakaoAccessTokenReqDto kakaoAccessTokenReqDto) {
        String url = TOKEN_BASE_URL + "/oauth/token";

        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

        HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, kakaoAccessTokenReqDto.toMultiValueMap());

        KakaoAccessTokenResDto kakaoAccessTokenResDto = restTemplate.exchange(url, HttpMethod.POST, httpEntity, KakaoAccessTokenResDto.class).getBody();

        return kakaoAccessTokenResDto;

    }




    /**
     * kakao user info 조회
     * 사용자 정보 가져오기
     */
    public KakaoAccountInfoResDto getAccountInfo(String accessToken) {
        String url = ACCOUNT_BASE_URL + "/v2/user/me";

        HttpHeaders httpHeaders = generateHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpHeaders.add("Authorization", BEARER + accessToken);

        HttpEntity httpEntity = generateHttpEntity(httpHeaders);

        KakaoAccountInfoResDto kakaoAccountInfoResDto = restTemplate.exchange(url, HttpMethod.POST, httpEntity, KakaoAccountInfoResDto.class).getBody();

        return kakaoAccountInfoResDto;
    }





    /**
     * Header 생성
     */
    private HttpHeaders generateHeader(String name, String val){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(name.equals("Authorization")){
            httpHeaders.add(name, "Bearer " + val);
            return httpHeaders;
        }

        httpHeaders.add(name, val);
        return httpHeaders;
    }

    /**
     * HttpEntity 생성(header만)
     */
    private HttpEntity generateHttpEntity(HttpHeaders httpHeaders){
        return new HttpEntity<>(httpHeaders);
    }

    /**
     * HttpEntity 생성(header + body)
     */
    private HttpEntity generateHttpEntityWithBody(HttpHeaders httpHeaders, MultiValueMap body) {
        return new HttpEntity<>(body, httpHeaders);
    }




//    private boolean isCodeValid(String code){
//        if(code == null || code.equals(SUCCESS_CODE) || code.equals("A0002")) return true; // A0002는 거래내역이 없어서 발생하는 것
//
//        return false;
//    }



}
