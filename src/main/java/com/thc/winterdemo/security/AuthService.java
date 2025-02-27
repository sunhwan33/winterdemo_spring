package com.thc.winterdemo.security;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;


public interface AuthService {
    String createAccessToken(Long userId);

    String createRefreshToken(Long userId);
    Algorithm getTokenAlgorithm();
    Long verifyAccessToken(String accessToken) throws JWTVerificationException;
    void revokeRefreshToken(Long userId);
    Long verifyRefreshToken(String refreshToken) throws JWTVerificationException;
    String issueAccessToken(String refreshToken) throws JWTVerificationException;
}
