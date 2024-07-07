package assignment.spring.security;

import assignment.spring.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60;//한시간
    private static final String KEY_ROLES = "roles";

    private final UserService userService;

    @Value("${spring.jwt.secret}")
    private String secretKey;


    //토큰을 생성하는 메소드
    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_ROLES, roles);

        var now = new Date();//현재 시간

        var expireDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);//토큰 만료시간

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, this.secretKey)
                .compact();
    }

    //jwt 토큰으로 부터 인증정보를 가져오는 메소드
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = this.userService.loadUserByUsername(this.getUsername(jwt));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return this.parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token))
            return false;

        var claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    //토큰으로부터 Claims정보를 가져오는 메소드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();//Claims정보를 가지고 옴
        } catch (
                ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
