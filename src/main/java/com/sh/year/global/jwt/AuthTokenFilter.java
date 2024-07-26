package com.sh.year.global.jwt;

import com.sh.year.global.common.RedisConst;
import com.sh.year.global.exception.JwtCustomErrorCode;
import com.sh.year.global.exception.JwtCustomException;
import com.sh.year.global.security.CustomUserDetailsService;
import com.sh.year.api.jwt.application.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;


@Slf4j
@RequiredArgsConstructor
//@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;
    private static final String BEARER = "Bearer ";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info("======= Request Path =========");
        String requestURI = request.getRequestURI();
        log.info("======= " + requestURI+" =======");

        checkToken(request);

        // antMatcher + 정규식으로 표현해줘야함, return 던져버리기

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String[] excludePath = {
                "/api/login/kakao",
                "/api/token/reissue/access",
                "/api/token/reissue/refresh",
                "/login/oauth2/redirect",
                "/test/login",
                "/favicon.ico",
                "/swagger-ui/index.html",
                "/swagger-ui/favicon-16x16.png",
                "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/swagger-initializer.js",
                "/swagger-ui/swagger-ui-standalone-preset.js",
                "/swagger-ui/swagger-ui-bundle.js",
                "/swagger-ui/index.css",
                "/swagger-ui/swagger-ui.css",
                "/api-docs/swagger-config",
                "/api-docs/YEAR%20API",
                "/error"


        };

        String path = request.getRequestURI();

        return Arrays.stream(excludePath).anyMatch(path::startsWith);

    }


    /**
     * 토큰 검사
     * access_token, refresh_token 검사
     */
    private void checkToken(HttpServletRequest request) {
        checkAccessToken(request);
    }


    private boolean checkAccessToken(HttpServletRequest request){

        //accessToken 검사
        try{
            log.info("========= Check Access Token ==========");
            String accessToken = jwtUtils.getAccessTokenFromHeader(request);

            String refreshToken = jwtUtils.getRefreshTokenFromHeader(request);

            String accessTokenFromRedis = getAccessTokenFromRedis(accessToken, refreshToken);
            log.info("header : " + accessToken);
            log.info("redis : " + accessTokenFromRedis);

            jwtUtils.validateAccessToken(accessToken); // access token 형식, 만료시간 등 검사

            if (accessToken.equals(accessTokenFromRedis)) {
                setUserInSecurityContext(request, accessToken);

            }
            else{
                throw new JwtCustomException(JwtCustomErrorCode.AccessTokenNotMatchWithRedisException); // J006
            }

        }
        catch (JwtCustomException e){// accessToken 만료시 refreshToken 검사
            // refresh token이 건재하다면 access token만 재발급하면 된다.
            if(checkRefreshToken(request)){
                throw new JwtCustomException(JwtCustomErrorCode.AccessTokenExpiredException); // J005, AccessToken 만료 에러 보내주기
            }

        }

        return true;
    }



    private boolean checkRefreshToken(HttpServletRequest request){

        try {
            String refreshToken = jwtUtils.getRefreshTokenFromHeader(request);

            String refreshTokenFromRedis = getRefreshTokenFromRedis(refreshToken);

            log.info("========= Check Refresh Token==========");
            log.info("header : " + refreshToken);
            log.info("redis : " + refreshTokenFromRedis);

            jwtUtils.validateRefreshToken(refreshToken);

            if(refreshToken.equals(refreshTokenFromRedis)){
                setUserInSecurityContext(request, refreshToken);
            }
            else{
                throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenNotMatchWithRedisException); // J007
            }

        }
        // refresh_token 만료시 access, refresh 둘 다 발급하기
        catch (JwtCustomException ex){ // refreshToken 만료시 error 던지기
            throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenExpiredException); // J001
        }

        return true;
    }


    private void setUserInSecurityContext(HttpServletRequest request, String token) {
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromAccessToken(token);

        UserDetails userDetails = customUserDetailsService.loadUserByUsernameAndProvider(claimFromToken.getEmail(), claimFromToken.getProvider());


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
        null,
                userDetails.getAuthorities());

        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }



    private String getRefreshTokenFromRedis(String refreshToken){
        log.info("==== Get Refresh Token From Redis ====");
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromAccessToken(refreshToken);

        String refreshTokenFromRedis = tokenService.getTokenFromRedis(RedisConst.REFRESH_TOKEN.prefix() + claimFromToken.getUserId());
        log.info("refreshTokenFromRedis : " + refreshTokenFromRedis);
        if(refreshTokenFromRedis == null){
            log.info("===== Refresh Token From Redis is Null ====");
            throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenExpiredException);
        }

        return refreshTokenFromRedis;
    }



    private String getAccessTokenFromRedis(String accessToken, String refreshToken){
        log.info("==== Get Access Token From Redis ====");
        JwtClaimDto claimFromToken = jwtUtils.getClaimFromAccessToken(accessToken);

        String accessTokenFromRedis = tokenService.getTokenFromRedis(RedisConst.ACCESS_TOKEN.prefix() + claimFromToken.getUserId());
        log.info("accessTokenFromRedis : " + accessTokenFromRedis);

        if(accessTokenFromRedis == null){
            log.info("===== Access Token From Redis is Null ====");

            getRefreshTokenFromRedis(refreshToken);

            throw new JwtCustomException(JwtCustomErrorCode.AccessTokenExpiredException);
        }

        return accessTokenFromRedis.substring(BEARER.length());

    }



    private Long getUserIdFromToken(String token){
        JwtClaimDto userInfo = jwtUtils.getClaimFromAccessToken(token);

        return userInfo.getUserId();
    }


}
