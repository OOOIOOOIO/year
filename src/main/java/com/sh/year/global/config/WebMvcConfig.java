package com.sh.year.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * .yml에서 add-mappings을 false로 설정하면 스프링에서 기본적으로 제공하는 정적자원요청에 대한 매핑을 사용하지 않기 때문에 정적 리소스 접근을 위해 열어두어야 한다.
 *
 */
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }
}

/* '/js/**'로 호출하는 자원은 '/static/js/' 폴더 아래에서 찾는다. */
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/css/**'로 호출하는 자원은 '/static/css/' 폴더 아래에서 찾는다. */
//                registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/img/**'로 호출하는 자원은 '/static/img/' 폴더 아래에서 찾는s다. */
//                registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/").setCachePeriod(60 * 60 * 24 * 365);
                /* '/font/**'로 호출하는 자원은 '/static/font/' 폴더 아래에서 찾는다. */
//                registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/").setCachePeriod(60 * 60 * 24 * 365);
//                }