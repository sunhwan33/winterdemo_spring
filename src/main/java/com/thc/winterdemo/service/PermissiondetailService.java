package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissiondetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PermissiondetailService {
    DefaultDto.CreateResDto create(PermissiondetailDto.CreateServDto param);
    void update(PermissiondetailDto.UpdateServDto param);
    void delete(PermissiondetailDto.UpdateServDto param);
    //void deletes(PermissiondetailDto.DeletesReqDto param);
    List<PermissiondetailDto.DetailResDto> list(PermissiondetailDto.ListServDto param);
    PermissiondetailDto.DetailResDto detail(DefaultDto.DetailServDto param);
}
