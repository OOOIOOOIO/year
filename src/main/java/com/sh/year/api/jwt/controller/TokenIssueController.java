package com.sh.year.api.jwt.controller;

import com.sh.year.api.jwt.application.TokenService;
import com.sh.year.api.jwt.controller.dto.TokenIssueResDto;
import com.sh.year.global.jwt.JwtClaimDto;
import com.sh.year.global.jwt.JwtUtils;
import com.sh.year.global.resolver.tokeninfo.TokenFromHeader;
import com.sh.year.global.resolver.tokeninfo.TokenFromHeaderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/token/reissue/access")
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
    @PostMapping("/access-token")
    public ResponseEntity<String> reissueAccessToken(@TokenFromHeader TokenFromHeaderDto tokenFromHeaderDto) {

        JwtClaimDto claimFromRefreshToken = jwtUtils.getClaimFromRefreshToken(tokenFromHeaderDto.getRefreshToken());

        String accessToken = jwtUtils.generateAccessToken(claimFromRefreshToken.getUserId(), claimFromRefreshToken.getEmail(), claimFromRefreshToken.getProvider());

        tokenService.uploadTokenToRedis(accessToken); // redis에 쏘기

        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }


    /**
     * refeshToken 기간 만료시
     * J001
     * J007
     * --> accessToken, refreshToken 재발급
     *
     */
    @PostMapping("/refessh-token")
    public ResponseEntity<TokenIssueResDto> reissueRefreshToken(@TokenFromHeader TokenFromHeaderDto tokenFromHeaderDto){

        /**
         * claim 만료시간과 관계 있는지 확인하기
         */

        JwtClaimDto claimFromRefreshToken = jwtUtils.getClaimFromRefreshToken(tokenFromHeaderDto.getRefreshToken());

        String accessToken = jwtUtils.generateAccessToken(claimFromRefreshToken.getUserId(), claimFromRefreshToken.getEmail(), claimFromRefreshToken.getProvider());
        String refreshToken = jwtUtils.generateRefreshToken(claimFromRefreshToken.getUserId(), claimFromRefreshToken.getEmail(), claimFromRefreshToken.getProvider());

        tokenService.uploadTokenToRedis(accessToken); // redis에 쏘기
        tokenService.uploadTokenToRedis(refreshToken);

        return new ResponseEntity<>(new TokenIssueResDto(accessToken, refreshToken), HttpStatus.OK);
    }




}
