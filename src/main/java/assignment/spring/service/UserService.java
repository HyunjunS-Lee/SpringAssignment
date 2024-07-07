package assignment.spring.service;


import assignment.spring.expection.ErrorResponse;
import assignment.spring.model.Auth;
import assignment.spring.model.entity.User;
import assignment.spring.repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static assignment.spring.type.ErrorCode.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;//실제 구현체 어떤 빈을 쓸건지 정의해야함(AppConfig에서 함)
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)//사용자 찾기
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user -> " + username));//없을 시
    }

    //회원가입에 대한 기능
    public User register(Auth.SignUp user) {
        boolean exists = this.userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new ErrorResponse(USER_ID_ALREADY_EXIT);
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
/*        User newUser = user.toEntity();
        if (user.isPartner()) {
            newUser.setRoles(List.of("ROLE_MANAGER"));
        } else {
            newUser.setRoles(List.of("ROLE_CUSTOMER"));
        }*/

        return this.userRepository.save(user.toEntity());
    }

    //로그인할 때 검증을 하기 위한 메소드, 즉 패스워드 인증 작업
    public User authenticate(Auth.SignIn user) {

        var getUser = this.userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new ErrorResponse(ID_NOT_EXIT));

        if (!this.passwordEncoder.matches(user.getPassword(), getUser.getPassword())) {
            throw new ErrorResponse(PASSWORD_UN_MATCH);
        }

        return getUser;
    }
}