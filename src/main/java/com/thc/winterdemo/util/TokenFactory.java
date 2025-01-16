package com.thc.winterdemo.util;


import com.thc.winterdemo.domain.RefreshToken;
import com.thc.winterdemo.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TokenFactory {
    private final RefreshTokenRepository refreshTokenRepository;
    public TokenFactory(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    //리프레시 토큰 만들기
    public String generateRefreshToken(Long userId){
        //단 한개의 리프레시 토큰만 사용하는 경우!
        revokeRefreshToken(userId);
        int refreshExpire = 60*24*14; //14일
        String token = generate(userId, refreshExpire);
        System.out.println("Refresh token: " + token);
        RefreshToken refreshToken = RefreshToken.of(userId, token);
        refreshTokenRepository.save(refreshToken);
        return token;
    }

    //리프레쉬 토큰 유효성 검증
    //파라미터로 온 토큰의 객체의 userId와 토큰 복호화해서 얻어낸 userId 비교
    public Long validateRefreshToken(String token){
        RefreshToken refreshToken = refreshTokenRepository.findByContent(token).orElse(null);
        if(refreshToken == null){
            return (long) -200; //저장 제대로 되어있는 토큰이 아니라는 것.
        }
        Long userId = valid(token);
        Long tokenUserId = refreshToken.getUserId();

        //사용자 비교
        if(userId != tokenUserId){
            return (long) -400;
        }
        return userId;
    }
    //리프레쉬 토큰 제거
    public void revokeRefreshToken(Long userId){
        List<RefreshToken> refreshTokens = refreshTokenRepository.findByUserId(userId);
        if(refreshTokens !=null && refreshTokens.isEmpty()){
            refreshTokenRepository.deleteAll(refreshTokens);
        }

    }

    //엑세스 토큰 만들기
    public String generateAccessToken(String refreshToken){
        //받은 리프레시 토큰에 대해 유효성 검사 필요
        Long userId = validateRefreshToken(refreshToken);
        if (userId <0) return null;

        int accessExpire = 60*24*2; //2일
        return generate(userId, accessExpire);
    }

    //액세스 토큰 유효성 검증
    public Long validateAccessToken(String token){
        return valid(token);
    }

    //토큰 만들기 (userId / 만료일시)
    public String generate(Long userId, int minute){
        NowDate now = new NowDate();
        String due = now.due(minute);
        String token = userId + "_"+due;
        String returnValue ="";

        try{
            returnValue = AES256Cipher.AES_Encode(AES256Cipher.temp_key, token);
        }catch(Exception e){

        }

        return returnValue;
    }

    //토큰 유효성 검증
    public Long valid(String token){
        Long userId = null;
        String due = null;
        String keyValues="";

        try{
            keyValues = AES256Cipher.AES_Decode(AES256Cipher.temp_key, token);
        }catch(Exception e){

        }
        if(keyValues.isEmpty()){
            // throw new RuntimeException("no key values found");
            return (long) -400; //여기는 키가 비어있는 거지만, 만료된거라 동일하게 취급
        } else{
            String[] keys = keyValues.split("_");
            userId = Long.parseLong(keys[0]);
            due = keys[1];

            NowDate nowDate = new NowDate();
            String now = nowDate.getNow();

            // 배열 초기화
            String[] temp = {due, now};
            //오름차순 정렬
            Arrays.sort(temp);
            System.out.println(Arrays.toString(temp));

            //현재랑, 듀데이탈ㅇ 누가 먼저인지 확인해봅시다?!
            if(due.equals(temp[0])){
                //throw new RuntimeException("due day over");
                return (long) -400;
            }
            return userId;

        }


    }

}
