package com.example.blog;

import com.example.blog.interceptor.LoginCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터 추가
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/assets/**", "/css/**", "/*.ico", "/login", "/logout", "/", "/error", "/js/**", "/register", "/postList", "/category/**", "/postList/**", "/searchPost/**");
    }
}
