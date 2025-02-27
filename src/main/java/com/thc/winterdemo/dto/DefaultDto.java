package com.thc.winterdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;


public class DefaultDto {

    @SuperBuilder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseDto {
        String empty; // 이게 없으면 빌더가 잘 안되어요 ㅠㅠ
        public BaseDto afterBuild(BaseDto param) {
            //param => reqDto 를 넣어주면!!
            BeanUtils.copyProperties(param, this);
            //this 인 서비스 디티오를 돌려줍니다! 안에 있는 모든 필드값 카피도 해줍니다!
            return this;
        }
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class CreateReqDto extends BaseDto{

        private Boolean deleted;


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
    public static class UpdateReqDto extends BaseDto {

        private Long id;
        private Boolean deleted;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class DeletesReqDto  extends BaseDto{
        private List<Long> ids;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class DeletesServDto extends DeletesReqDto{
        private Boolean isAdmin;
        private Long reqUserId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class DetailResDto extends BaseDto{

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
    public static class DetailReqDto  extends BaseDto{
        private Long id;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class DetailServDto  extends DetailReqDto{
        private Boolean isAdmin;
        private Long reqUserId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @SuperBuilder
    @Setter
    @Getter
    public static class ListReqDto extends BaseDto{


        private Boolean deleted;

        private String sdate; //검색일 시작
        private String fdate; //검색일 종료
    }
}
