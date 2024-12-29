package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.SpostDto;
import org.springframework.stereotype.Service;
import com.thc.winterdemo.repository.SpostRepository;

import java.util.List;

@Service
public interface SpostService {
    SpostDto.CreateResDto create(SpostDto.CreateReqDto param);
    void update(SpostDto.UpdateReqDto param);

    void delete(Long id);
    //void deletes(SpostDto.DeletesReqDto param);
    List<SpostDto.DetailResDto> list(SpostDto.ListReqDto param);
    SpostDto.DetailResDto detail(Long id);
}
