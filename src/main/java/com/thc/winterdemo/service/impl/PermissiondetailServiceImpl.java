package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.Permissiondetail;
import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissiondetailDto;
import com.thc.winterdemo.mapper.PermissiondetailMapper;
import com.thc.winterdemo.repository.PermissiondetailRepository;
import com.thc.winterdemo.service.PermissiondetailService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissiondetailServiceImpl implements PermissiondetailService {
    private final PermissiondetailRepository permissiondetailRepository;
    private final PermissiondetailMapper permissiondetailMapper;
    public PermissiondetailServiceImpl(PermissiondetailRepository permissiondetailRepository, PermissiondetailMapper permissiondetailMapper) {
        this.permissiondetailRepository = permissiondetailRepository;
        this.permissiondetailMapper = permissiondetailMapper;
    }

    @Override
    public DefaultDto.CreateResDto create(PermissiondetailDto.CreateServDto param){
        //System.out.println("create");
        //if(!param.getIsAdmin()){ throw new NoPermissiondetailException("no permissiondetail"); }
        DefaultDto.CreateResDto res = permissiondetailRepository.save(param.toEntity()).toCreateResDto();
        return res;
    }
    @Override
    public void update(PermissiondetailDto.UpdateServDto param){
        System.out.println("update");
        Permissiondetail permissiondetail = permissiondetailRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            permissiondetail.setDeleted(param.getDeleted());
        }
        if(param.getPermissionId()!=null){
            permissiondetail.setPermissionId(param.getPermissionId());
        }
        if(param.getTarget()!=null){
            permissiondetail.setTarget(param.getTarget());
        }
        if(param.getFunc()!=null){
            permissiondetail.setFunc(param.getFunc());
        }
        permissiondetailRepository.save(permissiondetail);
    }

    @Override
    public void delete(PermissiondetailDto.UpdateServDto param) {
        update(PermissiondetailDto.UpdateServDto.builder().id(param.getId()).deleted(true).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }
//    @Override
//    public void deletes(PermissiondetailDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public PermissiondetailDto.DetailResDto detail(DefaultDto.DetailServDto param) {
        return get(param);
    }

    public PermissiondetailDto.DetailResDto get(DefaultDto.DetailServDto param) {
        PermissiondetailDto.DetailResDto res = permissiondetailMapper.detail(param.getId());

        return res;
    }
    public List<PermissiondetailDto.DetailResDto> detailList(List<PermissiondetailDto.DetailResDto> list, DefaultDto.DetailServDto param) {
        List<PermissiondetailDto.DetailResDto> newList = new ArrayList<>();
        for(PermissiondetailDto.DetailResDto each : list) {
            newList.add(get(DefaultDto.DetailServDto.builder().id(each.getId()).reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build()));
        }
        return newList;
    }

    @Override
    public List<PermissiondetailDto.DetailResDto> list(PermissiondetailDto.ListServDto param) {
        System.out.println("permissionId: "+param.getPermissionId()+" Deleted: "+param.getDeleted() );
            return detailList(permissiondetailMapper.list(param), DefaultDto.DetailServDto.builder().reqUserId(param.getReqUserId()).isAdmin(param.getIsAdmin()).build());
    }

}
