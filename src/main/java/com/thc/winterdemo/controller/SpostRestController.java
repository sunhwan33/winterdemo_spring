package com.thc.winterdemo.controller;

import com.thc.winterdemo.dto.SpostDto;
import com.thc.winterdemo.service.SpostService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/spost")
@RestController
public class SpostRestController {

    private final SpostService spostService;
    public SpostRestController(SpostService spostService) {this.spostService = spostService;}


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) // JSON으로 요청 받도록 설정
    public ResponseEntity<SpostDto.CreateResDto> create(@RequestBody SpostDto.CreateReqDto param){
        System.out.println("Title: " + param.getTitle());
        System.out.println("Content: " + param.getContent());
        System.out.println("UserId: " + param.getUserId());
        System.out.println("param: "+param);
        SpostDto.CreateResDto responseDto = spostService.create(param);

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody SpostDto.UpdateReqDto param){
        spostService.update(param);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody SpostDto.UpdateReqDto param){
        spostService.delete(param.getId());
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/list")
//    public ResponseEntity<Void> deletes(@RequestBody SpostDto.DeletesReqDto param){
//        spostService.deletes(param);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("detail")
    public ResponseEntity<SpostDto.DetailResDto> detail(@RequestParam Long id){
        return ResponseEntity.ok(spostService.detail(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<SpostDto.DetailResDto>> list(SpostDto.ListReqDto param){
        //System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );

        return ResponseEntity.ok(spostService.list(param));
    }
}
