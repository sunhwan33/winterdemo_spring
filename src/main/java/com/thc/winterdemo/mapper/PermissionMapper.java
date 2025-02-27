package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.PermissionDto;

import java.util.List;

public interface PermissionMapper {
    PermissionDto.DetailResDto detail(Long id);
    List<PermissionDto.DetailResDto> list(PermissionDto.ListReqDto param);
}
