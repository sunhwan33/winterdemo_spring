package com.thc.winterdemo.service.impl;

import com.amazonaws.services.quicksight.model.UserRole;
import com.thc.winterdemo.domain.RoleType;
import com.thc.winterdemo.domain.User;
import com.thc.winterdemo.domain.UserRoleType;
import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.UserDto;
import com.thc.winterdemo.mapper.UserMapper;
import com.thc.winterdemo.repository.RoleTypeRepository;
import com.thc.winterdemo.repository.UserRepository;
import com.thc.winterdemo.repository.UserRoleTypeRepository;
import com.thc.winterdemo.security.AuthService;
import com.thc.winterdemo.service.UserService;
import com.thc.winterdemo.util.TokenFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TokenFactory tokenFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleTypeRepository roleTypeRepository;
    private final UserRoleTypeRepository userRoleTypeRepository;
    private final AuthService authService;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, TokenFactory tokenFactory, BCryptPasswordEncoder bCryptPasswordEncoder, RoleTypeRepository roleTypeRepository, UserRoleTypeRepository userRoleTypeRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.tokenFactory = tokenFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleTypeRepository = roleTypeRepository;
        this.userRoleTypeRepository = userRoleTypeRepository;
        this.authService = authService;
    }

    @Override
    public DefaultDto.CreateResDto create(UserDto.CreateReqDto param){
        System.out.println("create");
        User user = userRepository.findByUsername(param.getUsername());
        if (user !=null){
            throw new RuntimeException("already exist");
        }

        //비번 암호화를 위한 코드
        param.setPassword(bCryptPasswordEncoder.encode(param.getPassword()));
        User newUser = userRepository.save(param.toEntity());

        //권한은 그냥 ROLE_USER로 강제 저장함! (임시코드)
        RoleType roleType = roleTypeRepository.findByTypeName("ROLE_USER");
        if(roleType ==null){
            roleType = new RoleType();
            roleType.setId("user");
            roleType.setTypeName("ROLE_USER");
            roleTypeRepository.save(roleType);
        }
        UserRoleType userRoleType = UserRoleType.of(newUser, roleType);
        userRoleTypeRepository.save(userRoleType);

        return newUser.toCreateResDto();
    }

    @Override
    public DefaultDto.CreateResDto signup(UserDto.CreateReqDto param){
        System.out.println("signup");
        return create(param);
    }

    /*@Override
    public UserDto.LoginResDto login(UserDto.LoginReqDto param){
        System.out.println("login");
        User user = userRepository.findByUsernameAndPassword(param.getUsername(), param.getPassword());
        if (user == null){
            throw new RuntimeException("username or password incorrect");
        }
        //리프레쉬토큰 만들어야함
        String refreshToken = authService.createRefreshToken(user.getId());
        System.out.println("refreshToken : "+ refreshToken);
        return UserDto.LoginResDto.builder().refreshToken(refreshToken).build();
    }*/
    @Override
    public void logout(Long reqUserId){
        System.out.println("logout");
        User user = userRepository.findById(reqUserId).orElseThrow(()-> new RuntimeException("user not found"));
        /*tokenFactory.revokeRefreshToken(user.getId());*/
        authService.revokeRefreshToken(user.getId());
    }
    @Override
    public void update(UserDto.UpdateReqDto param){
        System.out.println("update");
        User user = userRepository.findById(param.getId()).orElseThrow(()-> new RuntimeException(""));
        if(param.getDeleted() != null) {
            user.setDeleted(param.getDeleted());
        }
        if(param.getName()!=null){
            user.setUsername(param.getName());
        }
        if(param.getPassword()!=null){
            user.setPassword(param.getPassword());
        }
        userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        update(UserDto.UpdateReqDto.builder().id(id).deleted(true).build());
    }
//    @Override
//    public void deletes(UserDto.DeletesReqDto param) {
//        for(Long id : param.getIds()){
//            delete(id);
//        }
//    }

    @Override
    public UserDto.DetailResDto detail(Long id) {
        return get(id);
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
public List<UserDto.DetailResDto> list(UserDto.ListReqDto param) {
    System.out.println("username: "+param.getUsername()+" Deleted: "+param.getDeleted() );
        return detailList(userMapper.list(param));
}

}
