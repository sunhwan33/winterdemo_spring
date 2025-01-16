package com.thc.winterdemo.service.impl;

import com.thc.winterdemo.domain.User;
import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.UserDto;
import com.thc.winterdemo.mapper.UserMapper;
import com.thc.winterdemo.repository.UserRepository;
import com.thc.winterdemo.service.UserService;
import com.thc.winterdemo.util.TokenFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenFactory tokenFactory;
    public UserServiceImpl(
            UserRepository userRepository
            , UserMapper userMapper
            , TokenFactory tokenFactory
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public UserDto.LoginResDto login(UserDto.LoginReqDto param) {
        System.out.println("login");
        User user = userRepository.findByUsernameAndPassword(param.getUsername(), param.getPassword());
        if(user == null){
            throw new RuntimeException("username or password incorrect");
        }

        String refreshToken = tokenFactory.generateRefreshToken(user.getId());
        System.out.println("refreshToken: " + refreshToken);

        return UserDto.LoginResDto.builder().refreshToken(refreshToken).build();
    }
    @Override
    public void logout(Long id){
        System.out.println("logout");
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found"));
        tokenFactory.revokeRefreshToken(id);
    }
    @Override
    public DefaultDto.CreateResDto signup(UserDto.CreateReqDto param) {
        System.out.println("signup");

        return create(param);
    }


    /**/

    @Override
    public DefaultDto.CreateResDto create(UserDto.CreateReqDto param) {
        System.out.println("create");
        User user = userRepository.findByUsername(param.getUsername());
        if(user != null){
            //return DefaultDto.CreateResDto.builder().id((long) -400).build();
            throw new RuntimeException("already exist");
        }
        return userRepository.save(param.toEntity()).toCreateResDto();
    }
    @Override
    public void update(UserDto.UpdateReqDto param) {
        System.out.println("update");
        User user = userRepository.findById(param.getId()).orElseThrow(() -> new RuntimeException(""));
        if(param.getDeleted() != null) {
            user.setDeleted(param.getDeleted());
        }
        if(param.getName() != null) {
            user.setName(param.getName());
        }
        if(param.getPhone() != null) {
            user.setPhone(param.getPhone());
        }
        userRepository.save(user);
    }
    @Override
    public void delete(Long id) {
        update(UserDto.UpdateReqDto.builder().id(id).deleted(true).build());
    }
    @Override
    public void deletes(DefaultDto.DeletesReqDto param) {
        for(Long id : param.getIds()){
            delete(id);
        }
    }

    public UserDto.DetailResDto get(Long id) {
        return userMapper.detail(id);
    }
    public List<UserDto.DetailResDto> detailList(List<UserDto.DetailResDto> list) {
        List<UserDto.DetailResDto> newList = new ArrayList<>();
        for(UserDto.DetailResDto each : list) {
            newList.add(get(each.getId()));
        }
        return newList;
    }
    @Override
    public UserDto.DetailResDto detail(Long id) {
        return get(id);
    }
    @Override
    public List<UserDto.DetailResDto> list(UserDto.ListReqDto param) {
        return detailList(userMapper.list(param));
    }

    @Override
    public DefaultDto.PagedListResDto pagedList(UserDto.PagedListReqDto param){
        DefaultDto.PagedListResDto retrunVal = DefaultDto.PagedListResDto.init(param, userMapper.pagedListCount(param));
        retrunVal.setList(detailList(userMapper.pagedList(param)));
        return retrunVal;
    }
    @Override
    public List<UserDto.DetailResDto> scrollList(UserDto.ScrollListReqDto param){
        param.init();
        Long cursor = param.getCursor();
        if(cursor != null){
            User user = userRepository.findById(cursor).orElseThrow(() -> new RuntimeException(""));
            param.setCreatedAt(user.getCreatedAt() + "");
        }
        return detailList(userMapper.scrollList(param));
    }

}
