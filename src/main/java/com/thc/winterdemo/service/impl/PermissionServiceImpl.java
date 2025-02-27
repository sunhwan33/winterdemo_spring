package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.Permission;
import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionDto;
import com.thc.winterdemo.dto.PermissiondetailDto;
import com.thc.winterdemo.dto.PermissionuserDto;
import com.thc.winterdemo.mapper.PermissionMapper;
import com.thc.winterdemo.repository.PermissionRepository;
import com.thc.winterdemo.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionMapper permissionMapper;
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public DefaultDto.CreateResDto create(PermissionDto.CreateServDto param){
        //System.out.println("create");
        //if(!param.getIsAdmin()){ throw new NoPermissionException("no permission"); }
        DefaultDto.CreateResDto res = permissionRepository.save(param.toEntity()).toCreateResDto();
        return res;
    }
    @Override
    public void update(PermissionDto.UpdateServDto param){
        System.out.println("update");
        Permission permission = permissionRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            permission.setDeleted(param.getDeleted());
        }
        if(param.getTitle()!=null){
            permission.setTitle(param.getTitle());
        }
        if(param.getContent()!=null){
            permission.setContent(param.getContent());
        }
        permissionRepository.save(permission);
    }

    @Override
    public void delete(PermissionDto.UpdateServDto param) {
        update(PermissionDto.UpdateServDto.builder().id(param.getId()).deleted(true).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }
//    @Override
//    public void deletes(PermissionDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public PermissionDto.DetailResDto detail(DefaultDto.DetailServDto param) {
        return get(param);
    }

    public PermissionDto.DetailResDto get(DefaultDto.DetailServDto param) {
        PermissionDto.DetailResDto res = permissionMapper.detail(param.getId());
        List<PermissiondetailDto.DetailResDto> details = permissoindetailService.list(PermissiondetailDto.ListServDto.builder().reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build())
        return res;


    }
    public List<PermissionDto.DetailResDto> detailList(List<PermissionDto.DetailResDto> list, DefaultDto.DetailServDto param) {
        List<PermissionDto.DetailResDto> newList = new ArrayList<>();
        for(PermissionDto.DetailResDto each : list) {
            newList.add(get(DefaultDto.DetailServDto.builder().id(each.getId()).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build()));
        }
        return newList;
    }

    @Override
    public List<PermissionDto.DetailResDto> list(PermissionDto.ListServDto param) {
        System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );
            return detailList(permissionMapper.list(param), DefaultDto.DetailServDto.builder().reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }

}
