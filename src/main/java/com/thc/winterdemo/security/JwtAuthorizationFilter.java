package com.thc.winterdemo.security;

import com.thc.winterdemo.exception.NoMatchingDataException;
import com.thc.winterdemo.repository.UserRepository;
import com.thc.winterdemo.domain.User;
import java.io.IOException;
import java.util.function.Supplier;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;
    private final AuthService authService;
    private ExternalProperties externalProperties;

    public JwtAuthorizationFilter (AuthenticationManager authenticationManager, UserRepository userRepository, AuthService authService, ExternalProperties externalProperties)
    {
        super(authenticationManager);
        this.userRepository = userRepository;
        this.authService = authService;
        this.externalProperties = externalProperties;
    }
    /*
    * 권한 인가를 위한 함수
    * AccessToken을 검증하고 유효하면 Authentication을 직접 생성해 SecurityContextHolder에 넣는다*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
        System.out.println("request!!!: "+request);
        String jwtHeader = request.getHeader(externalProperties.getAccessKey());
        System.out.println("jwtHeader: "+jwtHeader);
        if(jwtHeader == null || !jwtHeader.startsWith(externalProperties.getTokenPrefix())){
            //토큰 없을 시 Authentication 없는 상태로 doFilter -> Spring Security가 잡아낸다
            System.out.println("jwtHeader null");
            chain.doFilter(request, response);
            return;
        }
        String accessToken = jwtHeader.substring(externalProperties.getTokenPrefix().length());
        System.out.println("jwtHeader : "+accessToken);
        Long userId = authService.verifyAccessToken(accessToken);
        System.out.println("userId :"+userId);


        //유저 조회 없을 시 return NoMatchingDataException(404)
        User userEntity = userRepository.findEntityGraphRoleTypeById(userId).orElseThrow(new Supplier<NoMatchingDataException>(){
           @Override
           public NoMatchingDataException get(){return new NoMatchingDataException("id: "+userId );}
        });
        PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
        //Authentication 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        //SecurityContextHolder에 Authentication을 담아서 Spring Security가 권한처리 할 수 있게 한다
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

}
