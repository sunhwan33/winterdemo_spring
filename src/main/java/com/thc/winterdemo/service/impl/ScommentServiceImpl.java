package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.Scomment;
import com.thc.winterdemo.dto.ScommentDto;
import com.thc.winterdemo.mapper.ScommentMapper;
import com.thc.winterdemo.repository.ScommentRepository;
import com.thc.winterdemo.service.ScommentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScommentServiceImpl implements ScommentService {
    private final ScommentRepository scommentRepository;
    private final ScommentMapper scommentMapper;
    public ScommentServiceImpl(ScommentRepository scommentRepository, ScommentMapper scommentMapper) {
        this.scommentRepository = scommentRepository;
        this.scommentMapper = scommentMapper;
    }

    @Override
    public ScommentDto.CreateResDto create(ScommentDto.CreateReqDto param){
        //System.out.println("create");
        return scommentRepository.save(param.toEntity()).toCreateResDto();
    }
    public void update(ScommentDto.UpdateReqDto param){
        System.out.println("update");
        Scomment scomment = scommentRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            scomment.setDeleted(param.getDeleted());
        }
        if(param.getComment()!=null){
            scomment.setComment(param.getComment());
        }

        scommentRepository.save(scomment);
    }

    @Override
    public void delete(Long id) {
        update(ScommentDto.UpdateReqDto.builder().id(id).deleted(true).build());
    }
//    @Override
//    public void deletes(ScommentDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public ScommentDto.DetailResDto detail(Long id) {
        return get(id);
    }

    public ScommentDto.DetailResDto get(Long id) {
    return scommentMapper.detail(id);
}
    public List<ScommentDto.DetailResDto> detailList(List<ScommentDto.DetailResDto> list) {
        List<ScommentDto.DetailResDto> newList = new ArrayList<>();
        for(ScommentDto.DetailResDto each : list) {
            newList.add(get(each.getId()));
        }
        return newList;
    }

@Override
public List<ScommentDto.DetailResDto> list(ScommentDto.ListReqDto param) {
    System.out.println("comment: "+param.getComment()+" Deleted: "+param.getDeleted() );
        return detailList(scommentMapper.list(param));
}

}
