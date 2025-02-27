package com.thc.winterdemo.dto;
import com.thc.winterdemo.domain.Permissiondetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

public class PermissiondetailDto {

    
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateReqDto extends DefaultDto.CreateReqDto{
        private Long permissionId;
        private String target;
        private String func;


    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateServDto extends CreateReqDto {
        private Boolean isAdmin;
        private Long reqUserId;
        public Permissiondetail toEntity() {return Permissiondetail.of(getPermissionId(), getTarget, getFunc());}
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class UpdateReqDto extends DefaultDto.UpdateReqDto{
        private Long permissionId;
        private String target;
        private String func;
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
        private String target;
        private String func;
        private String userUsername;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListReqDto extends DefaultDto.ListReqDto{
        private Long permissionId;
        private String target;
        private String func;
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
