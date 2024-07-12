package com.sh.year.global.config;

import com.sh.year.global.resolver.token.reissue.TokenForReIssueFromHeaderArgumentResolver;
import com.sh.year.global.resolver.token.userinfo.UserInfoFromHeaderArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


/**
 * .yml에서 add-mappings을 false로 설정하면 스프링에서 기본적으로 제공하는 정적자원요청에 대한 매핑을 사용하지 않기 때문에 정적 리소스 접근을 위해 열어두어야 한다.
 *
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final UserInfoFromHeaderArgumentResolver userInfoFromHeaderArgumentResolver;
    private final TokenForReIssueFromHeaderArgumentResolver tokenForReIssueFromHeaderArgumentResolver;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoFromHeaderArgumentResolver);
        resolvers.add(tokenForReIssueFromHeaderArgumentResolver);
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://211.42.156.199:3000")
//                .allowedMethods("*")
//                .allowedHeaders("*")
////                .allowedMethods("HEAD", "GET", "POST", "PUT", "DELETE")
////                .allowedHeaders("Authorization", "Content-Type", "refresh_token")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
