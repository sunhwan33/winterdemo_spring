package com.thc.winterdemo.controller;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionDto;
import com.thc.winterdemo.security.PrincipalDetails;
import com.thc.winterdemo.service.PermissionService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/permission")
@RestController
public class PermissionRestController {

    private final PermissionService permissionService;
    public PermissionRestController(PermissionService permissionService) {this.permissionService = permissionService;}

    /**/
    public Long getRegUserId(PrincipalDetails principalDetails) {
        Long reqUserId = null;
        if(principalDetails != null&& principalDetails.getUser() != null) {
            reqUserId = principalDetails.getUser().getId();
        }
        return reqUserId;
    }

    @PostMapping("") // JSON으로 요청 받도록 설정
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody PermissionDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long reqUserId = getRegUserId(principalDetails);
        PermissionDto.CreateServDto newParam = (PermissionDto.CreateServDto) PermissionDto.CreateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        DefaultDto.CreateResDto responseDto = permissionService.create(newParam);

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody PermissionDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissionDto.UpdateServDto newParam = (PermissionDto.UpdateServDto) PermissionDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissionService.update(newParam);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody PermissionDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissionDto.UpdateServDto newParam = (PermissionDto.UpdateServDto) PermissionDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissionService.delete(newParam);
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/list")
//    public ResponseEntity<Void> deletes(@RequestBody PermissionDto.DeletesReqDto param){
//        permissionService.deletes(param);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("detail")
    public ResponseEntity<PermissionDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        DefaultDto.DetailServDto newParam = (DefaultDto.DetailServDto) DefaultDto.DetailServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissionService.detail(newParam));
    }
    @GetMapping("/list")
    public ResponseEntity<List<PermissionDto.DetailResDto>> list(PermissionDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );
        Long reqUserId = getRegUserId(principalDetails);
        PermissionDto.ListServDto newParam = (PermissionDto.ListServDto) PermissionDto.ListServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissionService.list(newParam));
    }
}
