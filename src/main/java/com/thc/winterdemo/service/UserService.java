package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    UserDto.LoginResDto login(UserDto.LoginReqDto param);
    DefaultDto.CreateResDto signup(UserDto.CreateReqDto param);
    void logout(Long reqUserId);
    /**/

    DefaultDto.CreateResDto create(UserDto.CreateReqDto param);
    void update(UserDto.UpdateReqDto param);
    void delete(Long id);
    void deletes(DefaultDto.DeletesReqDto param);
    UserDto.DetailResDto detail(Long id);
    List<UserDto.DetailResDto> list(UserDto.ListReqDto param);
    DefaultDto.PagedListResDto pagedList(UserDto.PagedListReqDto param);
    List<UserDto.DetailResDto> scrollList(UserDto.ScrollListReqDto param);


}
