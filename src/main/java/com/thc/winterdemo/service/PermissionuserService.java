package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionuserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissionuserService {
    DefaultDto.CreateResDto create(PermissionuserDto.CreateServDto param);
    void update(PermissionuserDto.UpdateServDto param);
    void delete(PermissionuserDto.UpdateServDto param);
    //void deletes(PermissionuserDto.DeletesReqDto param);
    List<PermissionuserDto.DetailResDto> list(PermissionuserDto.ListServDto param);
    PermissionuserDto.DetailResDto detail(DefaultDto.DetailServDto param);
}
