package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.Permissionuser;
import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionuserDto;
import com.thc.winterdemo.mapper.PermissionuserMapper;
import com.thc.winterdemo.repository.PermissionuserRepository;
import com.thc.winterdemo.service.PermissionuserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionuserServiceImpl implements PermissionuserService {
    private final PermissionuserRepository permissionuserRepository;
    private final PermissionuserMapper permissionuserMapper;
    public PermissionuserServiceImpl(PermissionuserRepository permissionuserRepository, PermissionuserMapper permissionuserMapper) {
        this.permissionuserRepository = permissionuserRepository;
        this.permissionuserMapper = permissionuserMapper;
    }

    @Override
    public DefaultDto.CreateResDto create(PermissionuserDto.CreateServDto param){
        //System.out.println("create");
        //if(!param.getIsAdmin()){ throw new NoPermissionuserException("no permissionuser"); }
        DefaultDto.CreateResDto res = permissionuserRepository.save(param.toEntity()).toCreateResDto();
        return res;
    }
    @Override
    public void update(PermissionuserDto.UpdateServDto param){
        System.out.println("update");
        Permissionuser permissionuser = permissionuserRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            permissionuser.setDeleted(param.getDeleted());
        }
        if(param.getPermissionId()!=null){
            permissionuser.setPermissionId(param.getPermissionId());
        }
        if(param.getUserId()!=null){
            permissionuser.setUserId(param.getUserId());
        }
        permissionuserRepository.save(permissionuser);
    }

    @Override
    public void delete(PermissionuserDto.UpdateServDto param) {
        update(PermissionuserDto.UpdateServDto.builder().id(param.getId()).deleted(true).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }
//    @Override
//    public void deletes(PermissionuserDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public PermissionuserDto.DetailResDto detail(DefaultDto.DetailServDto param) {
        return get(param);
    }

    public PermissionuserDto.DetailResDto get(DefaultDto.DetailServDto param) {
        PermissionuserDto.DetailResDto res = permissionuserMapper.detail(param.getId());

        return res;
    }
    public List<PermissionuserDto.DetailResDto> detailList(List<PermissionuserDto.DetailResDto> list, DefaultDto.DetailServDto param) {
        List<PermissionuserDto.DetailResDto> newList = new ArrayList<>();
        for(PermissionuserDto.DetailResDto each : list) {
            newList.add(get(DefaultDto.DetailServDto.builder().id(each.getId()).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build()));
        }
        return newList;
    }

    @Override
    public List<PermissionuserDto.DetailResDto> list(PermissionuserDto.ListServDto param) {
        System.out.println("permissionId: "+param.getPermissionId()+" Deleted: "+param.getDeleted() );
            return detailList(permissionuserMapper.list(param), DefaultDto.DetailServDto.builder().reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }

}
