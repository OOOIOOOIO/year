package com.sh.year.api.oauth;

import com.sh.year.api.oauth.dto.req.KakaoAuthCodeReqDto;
import com.sh.year.api.oauth.dto.res.KakaoAccessTokenResDto;
import com.sh.year.api.oauth.dto.res.KakaoAccountInfoResDto;
import com.sh.year.global.oauth.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthLoginApiController {

    private final OauthService oauthService;


    /**
     * kakao AuthCode 발급
     */
    @GetMapping("/kakao/code")
    public ResponseEntity<KakaoAuthCodeReqDto> 카카오코드발급(){

        KakaoAuthCodeReqDto kakaoAuthCodeReqDto = oauthService.makeAuthCodeDto();

        return new ResponseEntity<>(kakaoAuthCodeReqDto, HttpStatus.OK);
    }


    /**
     * kakao Access_token 발급(Callback)
     *
     * login 진행 및 사용자 정보 조회
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<KakaoAccountInfoResDto> kakaoLogin(@RequestParam("code") String code){

        // token 발급
        KakaoAccessTokenResDto kakaoAccessTokenResDto = oauthService.getAccessToken(code);

        // user info 조회
        KakaoAccountInfoResDto accountInfo = oauthService.getAccountInfo(kakaoAccessTokenResDto);

        return new ResponseEntity<>(accountInfo, HttpStatus.OK);
    }


}
