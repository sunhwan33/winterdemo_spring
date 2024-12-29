package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.SpostDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Spost extends AuditingFields {

    @Setter @Column(nullable = false)
    String title;
    @Setter @Column(nullable =true)
    String content;
    @Setter @Column(nullable = true)
    Long userId;

    protected Spost(){}
    private Spost(Boolean deleted, String title, String content, Long userId) {
        this.deleted = deleted;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
    public static Spost of(String title, String content, Long userId) {
        return new Spost(false, title, content, userId);

    }
    public SpostDto.CreateResDto toCreateResDto() {return SpostDto.CreateResDto.builder().id(getId()).build();}

}
