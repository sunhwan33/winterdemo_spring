package com.thc.winterdemo.controller;

import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.UserDto;
import com.thc.winterdemo.exception.NoAuthException;
import com.thc.winterdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/user")
@RestController
public class UserRestController {

    private final UserService userService;
    public UserRestController(UserService userService) {this.userService = userService;}

    public Long getReqUserId(HttpServletRequest request, boolean isAllowed) {
        Long reqUserId = null;
        Object tempReqUserId = request.getAttribute("reqUserId");
        if(tempReqUserId != null) {
            try{
                reqUserId = Long.parseLong(tempReqUserId.toString());
            }
            catch(Exception e) {}
        }
        if(reqUserId == null && !isAllowed) {
            throw new NoAuthException("Authorization not allowed");
        }
        return reqUserId;
    }

    @PostMapping("")
    public ResponseEntity<DefaultDto.CreateResDto> create(@RequestBody UserDto.CreateReqDto param){
        System.out.println("Username: " + param.getUsername());
        System.out.println("Password: " + param.getPassword());
        System.out.println("Name: " + param.getName());
        System.out.println("Phone: " + param.getPhone());
        System.out.println("param: "+param);
        DefaultDto.CreateResDto responseDto = userService.create(param);

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/signup")
    public ResponseEntity<DefaultDto.CreateResDto> signup(@RequestBody UserDto.CreateReqDto param){
        System.out.println("signup");
        return ResponseEntity.ok(userService.signup(param));
    }
    /*@PostMapping("/login")
    public ResponseEntity<UserDto.LoginResDto> login(@RequestBody UserDto.LoginReqDto param){
        return ResponseEntity.ok(userService.login(param));
    }*/
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        Long reqUserId = getReqUserId(request, false);
        userService.logout(reqUserId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("")
    public ResponseEntity<Void> update(@RequestBody UserDto.UpdateReqDto param){
        userService.update(param);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("")
    public ResponseEntity<Void> delete(@RequestBody UserDto.UpdateReqDto param){
        userService.delete(param.getId());
        return ResponseEntity.ok().build();
    }
//    @DeleteMapping("/list")
//    public ResponseEntity<Void> deletes(@RequestBody UserDto.DeletesReqDto param){
//        spostService.deletes(param);
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("detail")
    public ResponseEntity<UserDto.DetailResDto> detail(@RequestParam Long id){
        return ResponseEntity.ok(userService.detail(id));
    }
    @GetMapping("/list")
    public ResponseEntity<List<UserDto.DetailResDto>> list(UserDto.ListReqDto param){
        //System.out.println("title: "+param.getTitle()+" Deleted: "+param.getDeleted() );

        return ResponseEntity.ok(userService.list(param));
    }
}
