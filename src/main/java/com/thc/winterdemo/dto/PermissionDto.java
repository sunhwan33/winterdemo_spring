package com.thc.winterdemo.dto;
import com.thc.winterdemo.domain.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

public class PermissionDto {
    public static String[][] targets = {
            {"permission", "권한"}
            ,{"user", "사용자"}
            ,{"notice", "공지"}
    };
    
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateReqDto extends DefaultDto.CreateReqDto{
        private String title;
        private String content;


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateServDto extends CreateReqDto {
        private Boolean isAdmin;
        private Long reqUserId;
        public Permission toEntity() {return Permission.of(getTitle(), getContent());}
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto{
        private String title;
        private String content;

    }
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class UpdateServDto extends UpdateReqDto {
        private Boolean isAdmin;
        private Long reqUserId;
    }



    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DetailResDto extends DefaultDto.DetailResDto{
        private String title;
        private String content;
        private String[][] targets = PermissionDto.targets;
        private List<PermissionDto.DetailResDto> details;
        private List<PermissionDto.DetailResDto> users;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListReqDto extends DefaultDto.ListReqDto{
        private String title;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListServDto extends ListReqDto{
        private Boolean isAdmin;
        private Long reqUserId;
    }
}
