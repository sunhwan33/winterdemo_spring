package com.thc.winterdemo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.winterdemo.repository.UserRepository;
import com.thc.winterdemo.security.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final CorsFilterConfig corsFilterConfig;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final AuthService authService; // 토큰 팩토리라고 생각해주세요!
    private final ExternalProperties externalProperties;
    public SecurityConfig(CorsFilterConfig corsFilterConfig, UserRepository userRepository, ObjectMapper objectMapper, AuthService authService, ExternalProperties externalProperties){
        this.corsFilterConfig = corsFilterConfig;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }
    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*
    Spring Security 권한 설정
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->auth.anyRequest().permitAll())
                //세션을 안쓰는 경우 STATELESS!!
                .sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilter(corsFilterConfig.corsFilter())
                .apply(new CustomDsl());
        return http.build();
    }

    public class CustomDsl extends AbstractHttpConfigurer<CustomDsl, HttpSecurity>{
        /*
        Jwt Token Authentication을 위한 filter 설정

        jwtAuthenticationFilter : 인증을 위한 필터 ("/api/login")
        jwtAuthorizationFilter : 인가를 위한 필터
        FilterExceptionHandlerFIlter : TokenExpiredException 핸들링을 위한 필터
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, objectMapper, authService, externalProperties);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/login");

            http.addFilter(corsFilterConfig.corsFilter())
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, authService, externalProperties))
                    .addFilterBefore(new FilterExceptionHandlerFilter(), BasicAuthenticationFilter.class);
        }
    }
}
