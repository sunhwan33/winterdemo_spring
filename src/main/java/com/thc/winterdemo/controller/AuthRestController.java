package com.thc.winterdemo.controller;

import com.thc.winterdemo.util.TokenFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/api/auth")
@RestController
public class AuthRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TokenFactory tokenFactory;
    public AuthRestController(TokenFactory tokenFactory) {
        this.tokenFactory = tokenFactory;
    }

    @PostMapping("/accessToken")
    public ResponseEntity<String> accessToken(HttpServletRequest request) {
        String token = request.getHeader("RefreshToken");
        logger.info("RefreshToken [{}]", token);
        if(token ==null || token.isEmpty()) {
            throw new RuntimeException("not received refresh toeken");

        }
        return ResponseEntity.ok(tokenFactory.generateAccessToken(token));


    }
}
