package com.thc.winterdemo.security;

import com.amazonaws.services.cloudtrail.model.InvalidTokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.thc.winterdemo.domain.RefreshToken;
import com.thc.winterdemo.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class AuthServiceImpl implements AuthService{

    private final ExternalProperties externalProperties;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthServiceImpl(ExternalProperties externalProperties, RefreshTokenRepository refreshTokenRepository) {
        this.externalProperties = externalProperties;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Algorithm getTokenAlgorithm(){
        return Algorithm.HMAC512(externalProperties.getTokenSecretKey());
    }

    /*
    * Access Token 생성 위한 함수
    * Payload에 userId를 담는다*/
    @Override
    public String createAccessToken(Long userId){
        return JWT.create()
                .withSubject("accessToken")
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+externalProperties.getAccessTokenExpirationTime()))
                .sign(getTokenAlgorithm());
    }
    @Override
    public String createRefreshToken(Long userId){
        revokeRefreshToken(userId); //단 한개의 리프레시 토큰만 사용하는 경우!
        String refreshToken = JWT.create()
                .withSubject("refreshToken")
                .withClaim("id", userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+externalProperties.getRefreshTokenExpirationTime()))
                .sign(getTokenAlgorithm());

        //디비 저장
        /*RefreshToken refreshToken = RefreshToken.of(userId, token);
        refreshTokenRepository.save(refreshToken);*/
        refreshTokenRepository.save(RefreshToken.of(userId, refreshToken));
        return refreshToken;
    }

    public void revokeRefreshToken(Long userId){
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserId(userId);
        if(refreshTokens != null && refreshTokens.isEmpty()){
            refreshTokenRepository.deleteAll(refreshTokens);
        }
    }

    @Override
    public Long verifyAccessToken(String accessToken) throws JWTVerificationException {
        return JWT.require(getTokenAlgorithm())
                .build()
                .verify(accessToken)
                .getClaim("id").asLong();
    }

    @Override
    public Long verifyRefreshToken(String refreshToken) throws JWTVerificationException{
        refreshTokenRepository.findByContent(refreshToken)
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found"));
        return JWT.require(getTokenAlgorithm())
                .build()
                .verify(refreshToken)
                .getClaim("id").asLong();
    }

    /*
    * Access TOken 발급을 위한 함수
    * RefreshToken이 유요하다면 Access Token 발급*/

    @Override
    public String issueAccessToken(String refreshToken) throws JWTVerificationException{
        Long userId = verifyRefreshToken(refreshToken);
        String accessToken = createAccessToken(userId);
        return accessToken;
    }
    /*public Long validateAccessToken(String token) {
        //토큰을 받아서 유효여부 확인
        return valid(token);
    }*/
}
