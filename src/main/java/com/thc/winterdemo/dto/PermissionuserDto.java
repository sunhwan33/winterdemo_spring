package com.thc.winterdemo.dto;
import com.thc.winterdemo.domain.Permission;
import com.thc.winterdemo.domain.Permissionuser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class PermissionuserDto {

    
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateReqDto extends DefaultDto.CreateReqDto{
        private Long permissionId;
        private Long userId;
        private String username;


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateServDto extends CreateReqDto {
        private Boolean isAdmin;
        private Long reqUserId;
        public Permissionuser toEntity() {return Permissionuser.of(getPermissionId(), getUserId());}
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto{
        private Long permissionId;
        private Long userId;

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
        private Long permissionId;
        private Long userId;

        private String userUsername;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListReqDto extends DefaultDto.ListReqDto{
        private Long permissionId;
        private Long userId;
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
