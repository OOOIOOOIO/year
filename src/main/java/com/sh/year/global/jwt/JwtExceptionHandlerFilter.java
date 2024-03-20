package com.sh.year.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sh.year.global.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)  throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            log.error(e.getMessage());
            handleJwtCustomException(response, new JwtCustomException((JwtCustomErrorCode.SignatureException)));
        } catch (MalformedJwtException e) {
            log.error(e.getMessage());
            handleJwtCustomException(response, new JwtCustomException((JwtCustomErrorCode.MalformedJwtException)));
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            handleJwtCustomException(response, new JwtCustomException((JwtCustomErrorCode.AccessTokenExpiredException)));
        } catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
            handleJwtCustomException(response, new JwtCustomException((JwtCustomErrorCode.UnsupportedJwtException)));
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage());
            handleCustomException(response, new CustomException((CustomErrorCode.UsernameNotFoundException)));
        } catch (UsernameNotFoundException e) {
            log.error(e.getMessage());
            handleCustomException(response, new CustomException((CustomErrorCode.UsernameNotFoundException)));
        } catch (JwtCustomException e){
            log.error(e.getMessage());
            handleJwtCustomException(response, new JwtCustomException((e.getJwtCustomErrorCode())));
        }
    }

    private void handleJwtCustomException(HttpServletResponse response, JwtCustomException e) {

        log.error("handel JwtException : {}", e.getJwtCustomErrorCode());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 날짜 직렬화 문제

        response.setStatus(e.getJwtCustomErrorCode().getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.createJwtErrorResponse(e.getJwtCustomErrorCode());

        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException exception){
            exception.printStackTrace();
        }

    }

    private void handleCustomException(HttpServletResponse response, CustomException e) {

        log.error("handel CustomException : {}", e.getCustomErrorCode());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 날짜 직렬화 문제

        response.setStatus(e.getCustomErrorCode().getHttpStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = ErrorResponse.createCustomErrorResponse(e.getCustomErrorCode());

        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException exception){
            exception.printStackTrace();
        }

    }



}
