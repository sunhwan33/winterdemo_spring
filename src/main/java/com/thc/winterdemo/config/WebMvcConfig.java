package com.thc.winterdemo.config;

import com.thc.winterdemo.interceptor.DefaultInterceptor;
import com.thc.winterdemo.util.TokenFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final TokenFactory tokenFactory;
    public WebMvcConfig(TokenFactory tokenFactory) {this.tokenFactory = tokenFactory;}
    //인터셉터 설정을 위함
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new DefaultInterceptor(tokenFactory))
                .addPathPatterns("/api/**") //인터셉터가 실행되야 하는 url 패턴 설정
                .excludePathPatterns("/resources/**", "/api/tbuser/logout"); //인터셉터가 실행되지 않아야 하는 url 패턴 설정
    }
}
