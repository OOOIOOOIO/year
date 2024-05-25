package com.sh.year.global.config;

import com.sh.year.api.jwt.application.TokenService;
import com.sh.year.global.jwt.AuthEntryPointJwt;
import com.sh.year.global.jwt.AuthTokenFilter;
import com.sh.year.global.jwt.JwtExceptionHandlerFilter;
import com.sh.year.global.jwt.JwtUtils;
import com.sh.year.global.oauth.CustomOAuth2UserService;
import com.sh.year.global.oauth.CustomUserDetailsService;
import com.sh.year.global.oauth.handler.OAuth2AuthenticationSuccessHandlerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {  // extends WebSecurityConfigurerAdapte, Spring 2.7.0부터 Deprecated 됨(현재 프로젝트 2.7.6)

    private final AuthEntryPointJwt authEntryPointJwt;
//    private final AuthTokenFilter authTokenFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandlerImpl oAuth2AuthenticationSuccessHandler;
    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;



    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {

        return authConfig.getAuthenticationManager();
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{


//                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile",).permitAll() //정적리소스 물어보기
        http.cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                // disable session
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers("/api/token/reissue/**").permitAll()
                                .requestMatchers("/login/oauth2/code/**").permitAll()
                                .requestMatchers("/test/**").permitAll()
                                .requestMatchers("/favicon.ico").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/api-docs/**").permitAll()
//                                .requestMatchers(new AntPathRequestMatcher("/api/token/**")).permitAll()
                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated()
                )

                .exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPointJwt))

                .oauth2Login(oauth -> oauth.successHandler(oAuth2AuthenticationSuccessHandler)
                        .userInfoEndpoint(endpoint -> endpoint.userService(customOAuth2UserService))
                        .successHandler(oAuth2AuthenticationSuccessHandler)
//                        .failureHandler()

                );
        //실행순서 : userInfoEndpoint().userService -> successHandler()


//        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class); // 여기서 jwt 인증
        http.addFilterBefore(new AuthTokenFilter(jwtUtils, customUserDetailsService, tokenService), UsernamePasswordAuthenticationFilter.class); // 여기서 jwt 인증
        http.addFilterBefore(jwtExceptionHandlerFilter, AuthTokenFilter.class); // jwt 예외처리 필터

        return http.build();

    }

    /**
     * spirng security 6.X 부터 람다식으로 바뀜 쒯
     *
     * authorizeRequests deprecated
     *
     *                 .authorizeRequests()
     *                 .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile",).permitAll() //정적리소스 물어보기
     *                 .antMatchers("/api/users/**").permitAll()
     *                 .antMatchers("/api/issue").permitAll()
     *                 .antMatchers("/api/**").permitAll()
     *
     *                 대신
     *
     *                 .authorizeHttpRequests()
     *                 .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile",).permitAll() //정적리소스 물어보기
     *                 .requestMatchers("/api/users/**").permitAll()
     *                 .requestMatchers("/api/issue").permitAll()
     *                 .requestMatchers("/api/**").permitAll()
     *                 .anyRequest().authenticated();
     *
     */

}
