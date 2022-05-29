package kanfu.old_city_memo.security.jwt;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;
import kanfu.old_city_memo.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {
    @Value("${kanfu.old_city_memo.jwtSecret}")
    private String jwtSecret;

    @Value("${kanfu.old_city_memo.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateJwtToken(Authentication auth) {
        User userPrincipal = (User) auth.getPrincipal();
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("Invalid JWT token");
            return false;
        }
    }
}
