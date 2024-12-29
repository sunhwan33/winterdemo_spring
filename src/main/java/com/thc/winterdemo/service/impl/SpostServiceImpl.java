package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.Spost;
import com.thc.winterdemo.dto.SpostDto;
import com.thc.winterdemo.repository.SpostRepository;
import com.thc.winterdemo.service.SpostService;
import org.springframework.stereotype.Service;
import com.thc.winterdemo.mapper.SpostMapper;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpostServiceImpl implements SpostService {
    private final SpostRepository spostRepository;
    private final SpostMapper spostMapper;
    public SpostServiceImpl(SpostRepository spostRepository, SpostMapper spostMapper) {
        this.spostRepository = spostRepository;
        this.spostMapper = spostMapper;
    }

    @Override
    public SpostDto.CreateResDto create(SpostDto.CreateReqDto param){
        //System.out.println("create");
        return spostRepository.save(param.toEntity()).toCreateResDto();
    }
    public void update(SpostDto.UpdateReqDto param){
        System.out.println("update");
        Spost spost = spostRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            spost.setDeleted(param.getDeleted());
        }
        if(param.getTitle()!=null){
            spost.setTitle(param.getTitle());
        }
        if(param.getContent()!=null){
            spost.setContent(param.getContent());
        }
        spostRepository.save(spost);
    }

    @Override
    public void delete(Long id) {
        update(SpostDto.UpdateReqDto.builder().id(id).deleted(true).build());
    }
//    @Override
//    public void deletes(SpostDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public SpostDto.DetailResDto detail(Long id) {
        return get(id);
    }

    public SpostDto.DetailResDto get(Long id) {
    return spostMapper.detail(id);
}
    public List<SpostDto.DetailResDto> detailList(List<SpostDto.DetailResDto> list) {
        List<SpostDto.DetailResDto> newList = new ArrayList<>();
        for(SpostDto.DetailResDto each : list) {
            newList.add(get(each.getId()));
        }
        return newList;
    }

@Override
public List<SpostDto.DetailResDto> list(SpostDto.ListReqDto param) {
    System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );
        return detailList(spostMapper.list(param));
}

}
