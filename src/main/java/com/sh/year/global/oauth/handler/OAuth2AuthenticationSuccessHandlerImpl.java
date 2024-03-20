package com.sh.year.global.oauth.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.year.api.jwt.controller.dto.TokenIssueResDto;
import com.sh.year.global.jwt.JwtUtils;
import com.sh.year.api.jwt.application.TokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final TokenService tokenService;

    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        Long userId = oAuth2User.getAttribute("userId");
        String provider = oAuth2User.getAttribute("provider");
        String email = oAuth2User.getAttribute("email");


        // access_token 생성
        String accessToken = jwtUtils.generateAccessToken(userId, email, provider);

        // redis로 쏘기
        tokenService.uploadAccessTokenToRedis(accessToken, userId);

        // refresh_token 생성
        String refreshToken = jwtUtils.generateRefreshToken(userId, email, provider);

        // redis로 쏘기
        tokenService.uploadRefreshTokenToRedis(refreshToken, userId);


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        TokenIssueResDto tokenIssueResDto = new TokenIssueResDto(accessToken, refreshToken);

        String result = objectMapper.writeValueAsString(tokenIssueResDto);

        response.getWriter().write(result);

        log.info("=======onAuthenticationSuccess=======");
        log.info(accessToken);
        log.info(refreshToken);


    }
}
