package com.thc.winterdemo.service;

import com.thc.winterdemo.dto.ScommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScommentService {
    ScommentDto.CreateResDto create(ScommentDto.CreateReqDto param);
    void update(ScommentDto.UpdateReqDto param);

    void delete(Long id);
    //void deletes(ScommentDto.DeletesReqDto param);
    List<ScommentDto.DetailResDto> list(ScommentDto.ListReqDto param);
    ScommentDto.DetailResDto detail(Long id);
}
