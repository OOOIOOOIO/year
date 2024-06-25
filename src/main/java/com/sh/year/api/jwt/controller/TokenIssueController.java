package com.sh.year.api.jwt.controller;

import com.sh.year.api.jwt.application.TokenService;
import com.sh.year.api.jwt.controller.dto.TokenIssueResDto;
import com.sh.year.global.jwt.JwtClaimDto;
import com.sh.year.global.jwt.JwtUtils;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeader;
import com.sh.year.global.resolver.tokeninfo.UserInfoFromHeaderDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Token Controller", description = "Token API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token/reissue")
public class TokenIssueController {

    private final TokenService tokenService;
    private final JwtUtils jwtUtils;

    /**
     * 토큰 정보가 일치하지 않는 경우
     * J003
     * J004
     * J002
     * --> 다시 로그인 해주세요
     */


    /**
     * accessToken 기간 만료시
     * J005
     * J006
     * --> accessToken 재발급
     */
    @PostMapping("/access")
    public ResponseEntity<String> reissueAccessToken(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto) {


        String accessToken = jwtUtils.generateAccessToken(userInfoFromHeaderDto.getUserId(), userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider());

        tokenService.uploadAccessTokenToRedis(accessToken, userInfoFromHeaderDto.getUserId()); // redis에 쏘기

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    /**
     * refeshToken 기간 만료시
     * J001
     * J007
     * --> accessToken, refreshToken 재발급
     *
     */
    @PostMapping("/refessh")
    public ResponseEntity<TokenIssueResDto> reissueRefreshToken(@UserInfoFromHeader UserInfoFromHeaderDto userInfoFromHeaderDto){

        /**
         * claim 만료시간과 관계 있는지 확인하기
         */

        String accessToken = jwtUtils.generateAccessToken(userInfoFromHeaderDto.getUserId(), userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider());
        String refreshToken = jwtUtils.generateRefreshToken(userInfoFromHeaderDto.getUserId(), userInfoFromHeaderDto.getEmail(), userInfoFromHeaderDto.getProvider());

        tokenService.uploadAccessTokenToRedis(accessToken, userInfoFromHeaderDto.getUserId()); // redis에 쏘기
        tokenService.uploadRefreshTokenToRedis(refreshToken, userInfoFromHeaderDto.getUserId());

        return new ResponseEntity<>(new TokenIssueResDto(accessToken, refreshToken), HttpStatus.OK);
    }




}
