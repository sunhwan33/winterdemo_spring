package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.DefaultDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Permissionuser extends AuditingFields {

    @Setter @Column(nullable = false)
    Long permissionId;
    @Setter @Column(nullable =false)
    Long userId;


    protected Permissionuser(){}
    private Permissionuser(Boolean deleted, Long permissionId, Long userId) {
        this.deleted = deleted;
        this.permissionId = permissionId;
        this.userId = userId;
    }
    public static Permissionuser of(Long permissionId, Long userId) {
        return new Permissionuser(false, permissionId, userId);

    }
    public DefaultDto.CreateResDto toCreateResDto() {return DefaultDto.CreateResDto.builder().id(getId()).build();}

}
