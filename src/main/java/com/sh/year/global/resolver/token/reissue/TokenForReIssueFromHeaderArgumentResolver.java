package com.sh.year.global.resolver.token.reissue;

import com.sh.year.global.jwt.JwtClaimDto;
import com.sh.year.global.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Slf4j
@RequiredArgsConstructor
@Component
public class TokenForReIssueFromHeaderArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtils jwtUtils;


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(TokenForReIssueFromHeaderDto.class);
    }


    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        log.info("========= TokenForReIssueFromHeaderArgumentResolver ==========");

        String refreshToken = jwtUtils.getRefreshTokenFromHeader((HttpServletRequest) webRequest.getNativeRequest());

        JwtClaimDto claimFromAccessToken = null;

        claimFromAccessToken = jwtUtils.getClaimFromAccessToken(refreshToken);

        return new TokenForReIssueFromHeaderDto(claimFromAccessToken.getUserId(), claimFromAccessToken.getEmail(), claimFromAccessToken.getProvider());


    }
}
