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
        private List<String> roles;

        public User toEntity(){
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .roles(this.roles)
                    .build();
        }
    }
}