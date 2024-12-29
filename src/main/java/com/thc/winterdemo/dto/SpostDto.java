package com.thc.winterdemo.dto;
import com.thc.winterdemo.domain.Spost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class SpostDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateReqDto{
        private String title;
        private String content;
        private Long userId;
        private Boolean deleted;
        public Spost toEntity() {return Spost.of(getTitle(), getContent(), getUserId());}

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateResDto{
        private Long id;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class UpdateReqDto{
        private String title;
        private String content;
        private Long id;
        private Boolean deleted;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class DeletesReqDto {
        private List<Long> ids;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DetailResDto{
        private String title;
        private String content;
        private Long userId;
        private Long id;
        private Boolean deleted;
        private LocalDateTime createdAt;
        private String createdAtDateTime;
        private LocalDateTime modifiedAt;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListReqDto{
        private String title;
        //private Long id;
        private Boolean deleted;

        private String sdate; //검색일 시작
        private String fdate; //검색일 종료
    }
}
