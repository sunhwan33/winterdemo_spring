package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.DefaultDto;
import com.thc.winterdemo.dto.PermissionDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Permission extends AuditingFields {

    @Setter @Column(nullable = false)
    String title;
    @Setter @Column(nullable =true)
    String content;


    protected Permission(){}
    private Permission(Boolean deleted, String title, String content) {
        this.deleted = deleted;
        this.title = title;
        this.content = content;
    }
    public static Permission of(String title, String content) {
        return new Permission(false, title, content);

    }
    public DefaultDto.CreateResDto toCreateResDto() {return DefaultDto.CreateResDto.builder().id(getId()).build();}

}
