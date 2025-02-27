package com.thc.winterdemo.domain;


import com.thc.winterdemo.dto.ScommentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Scomment extends AuditingFields {

    @Setter @Column(nullable = false)
    String comment;


    protected Scomment(){}
    private Scomment(Boolean deleted, String comment) {
        this.deleted = deleted;
        this.comment = comment;
    }
    public static Scomment of(String comment) {
        return new Scomment(false, comment);

    }
    public ScommentDto.CreateResDto toCreateResDto() {return ScommentDto.CreateResDto.builder().id(getId()).build();}

}
