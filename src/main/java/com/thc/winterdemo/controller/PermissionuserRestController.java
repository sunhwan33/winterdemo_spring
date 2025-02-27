package com.thc.winterdemo.controller;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionuserDto;
import com.thc.winterdemo.security.PrincipalDetails;
import com.thc.winterdemo.service.PermissionuserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/permissionuser")
@RestController
public class PermissionuserRestController {

    private final PermissionuserService permissionuserService;
    public PermissionuserRestController(PermissionuserService permissionuserService) {this.permissionuserService = permissionuserService;}

    /**/
    public Long getRegUserId(PrincipalDetails principalDetails) {
        Long reqUserId = null;
        if(principalDetails != null&& principalDetails.getUser() != null) {
            reqUserId = principalDetails.getUser().getId();
        }
        return reqUserId;
    }

    @PostMapping("") // JSON으로 요청 받도록 설정
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody PermissionuserDto.CreateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long reqUserId = getRegUserId(principalDetails);
        PermissionuserDto.CreateServDto newParam = (PermissionuserDto.CreateServDto) PermissionuserDto.CreateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        DefaultDto.CreateResDto responseDto = permissionuserService.create(newParam);

        return ResponseEntity.ok(responseDto);
    }


    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody PermissionuserDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissionuserDto.UpdateServDto newParam = (PermissionuserDto.UpdateServDto) PermissionuserDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissionuserService.update(newParam);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody PermissionuserDto.UpdateReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        PermissionuserDto.UpdateServDto newParam = (PermissionuserDto.UpdateServDto) PermissionuserDto.UpdateServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        permissionuserService.delete(newParam);
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/list")
//    public ResponseEntity<Void> deletes(@RequestBody PermissionuserDto.DeletesReqDto param){
//        permissionuserService.deletes(param);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("detail")
    public ResponseEntity<PermissionuserDto.DetailResDto> detail(DefaultDto.DetailReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long reqUserId = getRegUserId(principalDetails);
        DefaultDto.DetailServDto newParam = (DefaultDto.DetailServDto) DefaultDto.DetailServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissionuserService.detail(newParam));
    }
    @GetMapping("/list")
    public ResponseEntity<List<PermissionuserDto.DetailResDto>> list(PermissionuserDto.ListReqDto param, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );
        Long reqUserId = getRegUserId(principalDetails);
        PermissionuserDto.ListServDto newParam = (PermissionuserDto.ListServDto) PermissionuserDto.ListServDto.builder().reqUserId(reqUserId).build().afterBuild(param);
        return ResponseEntity.ok(permissionuserService.list(newParam));
    }
}
