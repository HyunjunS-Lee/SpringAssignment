package assignment.spring.model;

import assignment.spring.model.entity.User;
import lombok.Data;

import java.util.List;

public class Auth {

    //로그인할 때 사용
    @Data
    public static class SignIn{
        private String username;
        private String password;
        private boolean isPartner;
    }

    //회원가입할 때 사용
    @Data
    public static class SignUp{
        private String username;
        private String password;
        // private boolean isPartner;
        //어떤 권한을 줄지(내부 로직으로 처리)
        //웹브라우저에서 일반회원가입 경로에서는 일반회원이 가질 수 있는 정보가 들어있고
        //관리자 페이지에서는 관리자가 사용할 수 있는 역할이 들어옴
        private List<String> roles;

        public User toEntity(){ //MemberEntity값을 바꿔줌
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .roles(this.roles)
                    //.roles(this.isPartner ? List.of("ROLE_MANAGER") : List.of("ROLE_CUSTOMER"))
                    .build();
        }
    }
}