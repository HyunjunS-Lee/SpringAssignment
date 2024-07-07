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

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user -> " + username));//없을 시
    }

    //회원가입에 대한 기능
    public User register(Auth.SignUp user) {
        boolean exists = this.userRepository.existsByUsername(user.getUsername());
        if (exists) {
            throw new ErrorResponse(USER_ID_ALREADY_EXIT);
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

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