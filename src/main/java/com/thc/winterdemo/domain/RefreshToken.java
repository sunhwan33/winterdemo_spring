package com.thc.winterdemo.domain;

import com.thc.winterdemo.dto.DefaultDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class RefreshToken extends AuditingFields{

    @Setter
    @Column(nullable = false)
    Long userId;
    @Setter @Column(nullable = false, unique = true)
    String content;

    protected RefreshToken() {}
    private RefreshToken(Boolean deleted, Long userId, String content) {
        this.deleted = deleted;
        this.userId = userId;
        this.content = content;
    }
    public static RefreshToken of(Long userId, String content) {
        return new RefreshToken(false, userId, content);
    }
    public DefaultDto.CreateResDto toCreateResDto() {return DefaultDto.CreateResDto.builder().id(getId()).build();}

}
