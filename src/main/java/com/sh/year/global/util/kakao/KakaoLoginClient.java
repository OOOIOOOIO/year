package com.sh.year.global.util.kakao;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sh.year.api.kakao.api.dto.KakaoAccessTokenReqDto;
import com.sh.year.api.kakao.api.dto.KakaoAccessTokenResDto;
import com.sh.year.api.kakao.api.dto.KakaoUserInfoResDto;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.log.LogTrace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginClient {

    private final RestTemplate restTemplate;
    private static final String BASE_URL = "https://kauth.kakao.com";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String PROVIDER = "kakao";
    @Value("${kakao.client-id}")
    private String clientId;
    @Value("${kakao.client-secret}")
    private String clientSecret;
    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    /**
     * AccessToken 발급
     */
    @LogTrace
    public String getAccessToken(String code){

        String accessToken = "";

        try{
            HttpHeaders httpHeaders = generateSingleHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            KakaoAccessTokenReqDto kakaoAccessTokenReqDto = KakaoAccessTokenReqDto.builder()
                    .grant_type(GRANT_TYPE)
                    .client_id(clientId)
                    .redirect_uri(redirectUri)
                    .code(code)
                    .client_secret(clientSecret)
                    .build();

            HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, kakaoAccessTokenReqDto.toMultiValueMap());

            KakaoAccessTokenResDto kakaoAccessTokenResDto = restTemplate.exchange(BASE_URL + "/oauth/token", HttpMethod.POST, httpEntity, KakaoAccessTokenResDto.class).getBody();

            accessToken = kakaoAccessTokenResDto.getAccess_token();

            return accessToken;

        }catch (Exception e){
            log.error("kakao code로 accessToken 발급 에러 : " + e.getMessage());
            throw new CustomException(CustomErrorCode.UserNotFoundException);
        }


    }



    @LogTrace
    public KakaoUserInfoResDto getUserInfo(String accessToken){

        try{
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
            headers.put("Authorization", "Bearer " + accessToken);

            HttpHeaders httpHeaders = generateMultiHeader(headers);

            HttpEntity httpEntity = generateHttpEntityWithBody(httpHeaders, null);

//            JSONObject body = restTemplate.exchange(BASE_URL + "/v2/user/me", HttpMethod.POST, httpEntity, JSONObject.class).getBody();
            String body = restTemplate.exchange(BASE_URL + "/v2/user/me", HttpMethod.POST, httpEntity, String.class).getBody();
            JsonElement parse = new JsonParser().parse(body);

            JsonObject kakaoAccount = parse.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            return new KakaoUserInfoResDto(email, PROVIDER);

        }catch (Exception e){
            log.error("kakao code로 accessToken 발급 에러 : " + e.getMessage());
            throw new CustomException(CustomErrorCode.UserNotFoundException);
        }

    }


    /**
     * SingleHeader 생성
     */
    private HttpHeaders generateSingleHeader(String name, String val){
        HttpHeaders httpHeaders = new HttpHeaders();
        if(name.equals("Authorization")){
            httpHeaders.add(name, "Bearer " + val);
            return httpHeaders;
        }

        httpHeaders.add(name, val);

        return httpHeaders;
    }


    /**
     * MultiHeader 생성
     */
    private HttpHeaders generateMultiHeader(Map<String, String> headers){
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setAll(headers);

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


}
