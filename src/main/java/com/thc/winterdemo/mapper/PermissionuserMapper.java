package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.PermissionDto;
import com.thc.winterdemo.dto.PermissionuserDto;

import java.util.List;

public interface PermissionuserMapper {
    PermissionuserDto.DetailResDto detail(Long id);
    List<PermissionuserDto.DetailResDto> list(PermissionuserDto.ListReqDto param);
}
