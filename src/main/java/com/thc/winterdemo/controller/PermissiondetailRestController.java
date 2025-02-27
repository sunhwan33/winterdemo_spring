package com.thc.winterdemo.controller;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissiondetailDto;
import com.thc.winterdemo.security.PrincipalDetails;
import com.thc.winterdemo.service.PermissiondetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/permissiondetail")
@RestController
public class PermissiondetailRestController {

    private final PermissiondetailService permissiondetailService;
    public PermissiondetailRestController(PermissiondetailService permissiondetailService) {this.permissiondetailService = permissiondetailService;}

    /**/
    public Long getRegUserId(PrincipalDetails principalDetails) {
        Long reqUserId = null;
        if(principalDetails != null&& principalDetails.getUser() != null) {
            reqUserId = principalDetails.getUser().getId();
        }
        return reqUserId;
    }

    @PostMapping("") // JSON으로 요청 받도록 설정
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody PermissiondetailDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long reqUserId = getRegUserId(principalDetails);
        PermissiondetailDto.CreateServDto newParam = (PermissiondetailDto.CreateServDto) PermissiondetailDto.CreateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        DefaultDto.CreateResDto responseDto = permissiondetailService.create(newParam);

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody PermissiondetailDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissiondetailDto.UpdateServDto newParam = (PermissiondetailDto.UpdateServDto) PermissiondetailDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissiondetailService.update(newParam);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody PermissiondetailDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissiondetailDto.UpdateServDto newParam = (PermissiondetailDto.UpdateServDto) PermissiondetailDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissiondetailService.delete(newParam);
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/list")
//    public ResponseEntity<Void> deletes(@RequestBody PermissiondetailDto.DeletesReqDto param){
//        permissiondetailService.deletes(param);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("detail")
    public ResponseEntity<PermissiondetailDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        DefaultDto.DetailServDto newParam = (DefaultDto.DetailServDto) DefaultDto.DetailServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissiondetailService.detail(newParam));
    }
    @GetMapping("/list")
    public ResponseEntity<List<PermissiondetailDto.DetailResDto>> list(PermissiondetailDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );
        Long reqUserId = getRegUserId(principalDetails);
        PermissiondetailDto.ListServDto newParam = (PermissiondetailDto.ListServDto) PermissiondetailDto.ListServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissiondetailService.list(newParam));
    }
}
