package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.DefaultDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Permissiondetail extends AuditingFields {
    @Setter @Column(nullable = false)
    Long permissionId;
    @Setter @Column(nullable = false)
    String target;
    @Setter @Column(nullable =false)
    String func;


    protected Permissiondetail(){}
    private Permissiondetail(Boolean deleted, Long permissionId, String target, String func) {
        this.deleted = deleted;
        this.permissionId = permissionId;
        this.target = target;
        this.func = func;
    }
    public static Permissiondetail of(Long permissionId, String target, String func) {
        return new Permissiondetail(false, permissionId, target, func);

    }
    public DefaultDto.CreateResDto toCreateResDto() {return DefaultDto.CreateResDto.builder().id(getId()).build();}

}
