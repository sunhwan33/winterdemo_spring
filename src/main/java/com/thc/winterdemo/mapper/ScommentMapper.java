package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.ScommentDto;

import java.util.List;

public interface ScommentMapper {
    ScommentDto.DetailResDto detail(Long id);
    List<ScommentDto.DetailResDto> list(ScommentDto.ListReqDto param);
}
