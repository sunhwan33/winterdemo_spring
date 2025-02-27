package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.SpostDto;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Entity
public class User extends AuditingFields {

    @Setter @Column(nullable = false, unique=true)
    String username;
    @Setter @Column(nullable =false)
    String password;
    @Setter
    String name;
    @Setter
    String phone;

    //fetch 타입 바꾸고, toString 순환 참조 수정
    @OneToMany(mappedBy = "user", fetch= FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<UserRoleType> userRoleType = new ArrayList<>();

    //권한 관련한 기능 추가
    public List<UserRoleType> getRoleList(){
        if(!this.userRoleType.isEmpty()){
            return userRoleType;
        }
        return new ArrayList<>();
    }



    protected User(){}
    private User(Boolean deleted, String username, String password, String name, String phone) {
        this.deleted = deleted;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }
    public static User of(String username, String password, String name, String phone) {
        return new User(false, username, password, name, phone);

    }
    public DefaultDto.CreateResDto toCreateResDto() {return DefaultDto.CreateResDto.builder().id(getId()).build();}

}
