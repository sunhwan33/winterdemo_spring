package com.thc.winterdemo.controller;

import com.thc.winterdemo.exception.NoAuthException;
import com.thc.winterdemo.security.AuthService;
import com.thc.winterdemo.security.ExternalProperties;
import com.thc.winterdemo.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthRestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*private final TokenFactory tokenFactory;*/
    private final AuthService authService;
    private final ExternalProperties externalProperties;
    public AuthRestController(
            //TokenFactory tokenFactory
            AuthService authService,
            ExternalProperties externalProperties) {
       /* this.tokenFactory = tokenFactory;*/
        this.authService = authService;
        this.externalProperties = externalProperties;
    }
    @PostMapping("/accessToken")
    public ResponseEntity<Void> accessToken(HttpServletRequest request) {
       /* String refreshToken = request.getHeader("RefreshToken");
        if(refreshToken == null || refreshToken.isEmpty()){
            throw new RuntimeException("Refresh token is empty");
        }
        return ResponseEntity.ok(tokenFactory.generateAccessToken(refreshToken));*/
        String jwtHeader = request.getHeader(externalProperties.getRefreshKey());
        if(jwtHeader == null || !jwtHeader.startsWith(externalProperties.getTokenPrefix())) {
            throw new NoAuthException("");
        }
        String refreshToken = jwtHeader.substring(externalProperties.getTokenPrefix().length());

        String accessToken = authService.issueAccessToken(refreshToken);
        System.out.println("Auth_restController :"+refreshToken+"|||| accessToken :"+accessToken);
        return ResponseEntity.status(HttpStatus.OK).header(externalProperties.getAccessKey(), externalProperties.getTokenPrefix()+accessToken).build();
    }
}
