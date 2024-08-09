package com.sh.year.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sh.year.global.exception.CustomErrorCode;
import com.sh.year.global.exception.CustomException;
import com.sh.year.global.exception.JwtCustomErrorCode;
import com.sh.year.global.exception.JwtCustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtInfoProperties jwtInfoProperties;
    private final ObjectMapper objectMapper;
    private final static String ACCESS_TOKEN = "access_token";
    private final static String REFRESH_TOKEN = "refresh_token";
    private final static String ACCESS_TOKEN_HEADER_NAME = "Authorization";
    private final static String REFRESH_TOKEN_HEADER_NAME = "refresh_token";
    private static final String BEARER = "Bearer ";


    /**
     * header에서 access_token 가져오기
     * BEARER 제거
     */
    public String getAccessTokenFromHeader(HttpServletRequest request) {

        log.info("========= Get AccessToken From Header ==========");

        String accessToken = request.getHeader(ACCESS_TOKEN_HEADER_NAME).substring(BEARER.length());
        log.info("=========" + accessToken + "===========");
        return accessToken;
//        return request.getHeader(ACCESS_TOKEN_HEADER_NAME).substring(BEARER.length());

    }

    /**
     * header에서 refresh_token 가져오기
     */
    public String getRefreshTokenFromHeader(HttpServletRequest request) {
        log.info("========= Get RefreshToken From Header ==========");
        String refreshToken = request.getHeader(REFRESH_TOKEN_HEADER_NAME);
        log.info("=========" + refreshToken + "===========");

        return refreshToken;

    }


    /**
     * subject 정보 가져오기(username)
     * subject
     * - email
     */
    public String getSubjectFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtInfoProperties.getSecret().getBytes()) // signature를 secrete key로 설정했는지, publickey로 설정했는지 확인! 나는 secret key로 설정
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }


    /**
     * accessToken claim 정보 가져오기
     *
     * JwtClaimDto
     * -
     * - userId
     * - email
     * - provider
     * - tokenType
     */
    public JwtClaimDto getClaimFromAccessToken(String token){
//        Object userInfo = Jwts.parserBuilder()
//                .setSigningKey(jwtInfoProperties.getSecret().getBytes())
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .get("userInfo");

        JwtClaimDto userInfo = objectMapper.convertValue(Jwts.parserBuilder()
                .setSigningKey(jwtInfoProperties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userInfo"), JwtClaimDto.class);

        return userInfo;
    }

    /**
     * accessToken claim 정보 가져오기
     *
     * JwtClaimDto
     * -
     * - userId
     * - email
     * - provider
     * - tokenType
     */
    public JwtClaimDto getClaimFromRefreshToken(String token){
        return (JwtClaimDto) Jwts.parserBuilder()
                .setSigningKey(jwtInfoProperties.getSecret().getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userInfo");

    }




    /**
     * JWT 토큰 생성
     * subject
     * - email
     *
     * claim
     * - JwtClaimDto
     *  - userId
     *  - email
     *  - provider
     *  - tokenType
     *
     * expireMin
     * - 14일(access)
     * - 28일(refresh)
     *
     */
    public String generateAccessToken(Long userId, String email, String provider) {
//        Key key = Keys.hmacShaKeyFor(encodeSecretKey(jwtInfoProperties.getSecret()).getBytes());
//        Key key = Keys.hmacShaKeyFor(jwtInfoProperties.getSecret().getBytes());
//        Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(jwtInfoProperties.getSecret().getBytes()).getBytes());
        String secretKey = Base64.getEncoder().encodeToString(jwtInfoProperties.getSecret().getBytes());

        return BEARER + Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(email)
                .claim("userInfo", new JwtClaimDto(userId, email, provider, ACCESS_TOKEN))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtInfoProperties.getAccessTokenExpireMin()))
//                .signWith(key, SignatureAlgorithm.HS256)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
    private String encodeSecretKey(String secretKey){
        String result = Base64.getEncoder().encodeToString(secretKey.getBytes());

        return result;
    }
    private byte[] decodeSecretKey(String secretKey){
        byte[] result = Base64.getDecoder().decode(secretKey);

        return result;
    }

    /**
     * Refresh token 생성
     *
     * expireMin
     * - 28일
     */
    public String generateRefreshToken(Long userId, String email, String provider) {
//        Key key = Keys.hmacShaKeyFor(encodeSecretKey(jwtInfoProperties.getSecret()).getBytes());
//        Key key = Keys.hmacShaKeyFor(jwtInfoProperties.getSecret().getBytes());
//        Key key = Keys.hmacShaKeyFor(Base64.getEncoder().encodeToString(jwtInfoProperties.getSecret().getBytes()).getBytes());
        String secretKey = Base64.getEncoder().encodeToString(jwtInfoProperties.getSecret().getBytes());
        /**
         * decode
         */

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setSubject(email)
                .claim("userInfo", new JwtClaimDto(userId, email, provider, REFRESH_TOKEN))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtInfoProperties.getRefreshTokenExpireMin()))
//                .signWith(key, SignatureAlgorithm.HS256)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     *  JWT 토큰 검사
     */
    public boolean validateAccessToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtInfoProperties.getSecret().getBytes()) // signature를 secrete key로 설정했는지, publickey로 설정했는지 확인! 나는 secret key로 설정
                    .build()
                    .parseClaimsJws(token);  // 여기서 Runtime Exception이 던져진다.

            return true;
        } catch (SignatureException e) {
            throw new JwtCustomException(JwtCustomErrorCode.SignatureException);
        } catch (MalformedJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.MalformedJwtException);
        } catch (ExpiredJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.AccessTokenExpiredException);
        } catch (UnsupportedJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.UnsupportedJwtException);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomErrorCode.IllegalArgumentException);
        }

    }

    public boolean validateRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtInfoProperties.getSecret().getBytes()) // signature를 secrete key로 설정했는지, publickey로 설정했는지 확인! 나는 secret key로 설정
                    .build()
                    .parseClaimsJws(token);  // 여기서 Runtime Exception이 던져진다.

            return true;
        } catch (SignatureException e) {
            throw new JwtCustomException(JwtCustomErrorCode.SignatureException);
        } catch (MalformedJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.MalformedJwtException);
        } catch (ExpiredJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.RefreshTokenExpiredException);
        } catch (UnsupportedJwtException e) {
            throw new JwtCustomException(JwtCustomErrorCode.UnsupportedJwtException);
        } catch (IllegalArgumentException e) {
            throw new CustomException(CustomErrorCode.IllegalArgumentException);
        }

    }




}