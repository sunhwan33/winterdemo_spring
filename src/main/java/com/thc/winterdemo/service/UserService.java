package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    DefaultDto.CreateResDto create(UserDto.CreateReqDto param);
    DefaultDto.CreateResDto signup(UserDto.CreateReqDto param);
    /*UserDto.LoginResDto login(UserDto.LoginReqDto param);*/
    void logout(Long reqUserId);
    void update(UserDto.UpdateReqDto param);
    void delete(Long id);
    //void deletes(UserDto.DeletesReqDto param);
    List<UserDto.DetailResDto> list(UserDto.ListReqDto param);
    UserDto.DetailResDto detail(Long id);
}
