package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.PermissiondetailDto;
import java.util.List;

public interface PermissiondetailMapper {
    PermissiondetailDto.DetailResDto detail(Long id);
    List<PermissiondetailDto.DetailResDto> list(PermissiondetailDto.ListReqDto param);
}
