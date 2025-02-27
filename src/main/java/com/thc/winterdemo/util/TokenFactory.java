package com.thc.winterdemo.util;

import com.thc.winterdemo.domain.RefreshToken;
import com.thc.winterdemo.repository.RefreshTokenRepository;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TokenFactory {
    private final RefreshTokenRepository refreshTokenRepository;
    public TokenFactory(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateRefreshToken(Long userId){
        revokeRefreshToken(userId); //단 한개의 리프레시 토큰만 사용하는 경우!
        int refreshExpire = 60*24*14 ;//14일
        String token = generate(userId, refreshExpire);
        System.out.println("Refresh token: " + token);
        RefreshToken refreshToken = RefreshToken.of(userId, token);
        refreshTokenRepository.save(refreshToken);
        return token;
    }
    public Long validateRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByContent(token).orElse(null);
        System.out.println("validatation-refresh : "+refreshToken);
        if(refreshToken == null){
            return (long) -200;
        }
        Long userId = valid(token);
        Long tokenUserId = refreshToken.getUserId();

        if(userId != tokenUserId){
            return (long) -400;
        }

        return userId;

    }
    public void revokeRefreshToken(Long userId){
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserId(userId);
        if(refreshTokens != null && refreshTokens.isEmpty()){
            refreshTokenRepository.deleteAll(refreshTokens);
        }
    }

    public String generateAccessToken(String refreshToken){
        Long userId = validateRefreshToken(refreshToken);
        System.out.println("getUserId : "+userId);
        if(userId <0) return null;
        int accessExpire = 60*24*2 ;//2일
        return generate(userId, accessExpire);

    }

    public Long validateAccessToken(String token) {
        //토큰을 받아서 유효여부 확인
        return valid(token);
    }

    public String generate(Long userId, int minute){
        NowDate nowDate = new NowDate();
        String due = nowDate.due(minute);
        String token = userId+"_"+due;
        String returnValue = "";

        try{
            returnValue = AES256Cipher.AES_Encode(AES256Cipher.temp_key, token);
        }catch (Exception e){

        }
        return returnValue;
    }
    public Long valid(String token) {
        /*
        1. userId, due, keyValues 널 값으로 설정

        2. keyValues에 복호화한 값 저장
        3-1. 만일 keyValues가 공백이면
            return 401
        3-2. 아니라면
            keys 리스트에 저장 "_"로 분할
            userId 값 설정
            due 설정

            NowDate 객체 불러오기
            due와 now 비교 (due가 먼저면 error)
         */
        Long userId = null;
        String due = null;
        String keyValues = "";

        try{
            keyValues = AES256Cipher.AES_Decode(AES256Cipher.temp_key, token);
        }catch(Exception e){

        }
        if(keyValues.isEmpty()){
            return (long) -400; //키가 비어있는 것 = 키가 만료된거라고 취급하자
        }
        else{
            String [] keys = keyValues.split("_");
            userId = Long.parseLong(keys[0]);
            due = keys[1];

            NowDate nowDate = new NowDate();
            String now = nowDate.getNow();

            String[] temps = {due, now};

            Arrays.sort(temps);
            //ex. due : 2024-01-01 / now : 2024-01-17
            //ex. ascending sort result : due, now
            // ===>>>> error

            if(due.equals(temps[0])){
                return (long) -400;

            }
            return userId;
        }

    }
}
