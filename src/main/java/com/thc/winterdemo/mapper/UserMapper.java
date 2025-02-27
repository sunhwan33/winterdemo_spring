package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto.DetailResDto detail(Long id);
    List<UserDto.DetailResDto> list(UserDto.ListReqDto param);
}
