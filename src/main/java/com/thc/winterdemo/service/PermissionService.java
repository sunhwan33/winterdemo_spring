package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionService {
    DefaultDto.CreateResDto create(PermissionDto.CreateServDto param);
    void update(PermissionDto.UpdateServDto param);
    void delete(PermissionDto.UpdateServDto param);
    //void deletes(PermissionDto.DeletesReqDto param);
    List<PermissionDto.DetailResDto> list(PermissionDto.ListServDto param);
    PermissionDto.DetailResDto detail(DefaultDto.DetailServDto param);
}
