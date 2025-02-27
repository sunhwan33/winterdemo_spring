package com.thc.winterdemo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thc.winterdemo.dto.UserDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;

@Transactional

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;
    private final AuthService authService;
    private final ExternalProperties externalProperties;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper, AuthService authService, ExternalProperties externalProperties) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }
    /*
    * 로그인하려는 사용자의 자격을 확인 -> 토큰을 발급
    * "/api/login" 으로 들어오는 요청에 실행됨
    * 생성된 Authentication이 SecurityContextHolder에 등록되어 권한처리가 가능하게 한다
    *
    * @throws AuthenticationException
    * */


    @Transactional
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        /*
        * 로그인에 필요한 아이디와 비번이 있는지 먼저 확인
        * 로그인에 필요한 아이디와 비번 -> 실제 존재하는 고객인지 확인 (이 정보로 Authentication 객체 만들어 리턴
        * */
        Authentication authentication = null;
        UserDto.LoginReqDto userLoginDto = null;

        try{
            userLoginDto = objectMapper.readValue(request.getInputStream(), UserDto.LoginReqDto.class);
        }catch(IOException e){
            System.out.println("1. login attemptAuthentication : Not Enough Parameters?!");
            //e.printStackTrace();
        }
        try{
            System.out.println("2q. login : " + userLoginDto.getUsername() +"//" + userLoginDto.getPassword());

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword());
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch(AuthenticationException e){
            System.out.println("2. login attemptAuthentication : username, password Not Matched?!");
            //e.printStackTrace();
        }
        return authentication;
    }

    /*
    * 로그인 완료시 호출되는 함수
    * RefreshToken을 발급해 HttpServletResponse에 담는다
    * */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,Authentication authResult) throws IOException, ServletException {
        /*
        * UserId로 리프레쉬토큰 발급
        * 헤더에 담아서 전달*/
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        System.out.println("principalDetails.getUser().getId(): "+principalDetails.getUser().getId());
        String refreshToken = authService.createRefreshToken(principalDetails.getUser().getId());
        response.addHeader(externalProperties.getRefreshKey(), externalProperties.getTokenPrefix()+refreshToken);
    }
}
