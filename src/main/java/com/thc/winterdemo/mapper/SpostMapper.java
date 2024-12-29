package com.thc.winterdemo.mapper;

import com.thc.winterdemo.dto.SpostDto;

import java.util.List;

public interface SpostMapper {
    SpostDto.DetailResDto detail(Long id);
    List<SpostDto.DetailResDto> list(SpostDto.ListReqDto param);
}
