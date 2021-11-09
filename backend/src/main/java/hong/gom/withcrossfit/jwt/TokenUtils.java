package hong.gom.withcrossfit.jwt;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenUtils {
	
	@Autowired
	private Environment env;
	
    private String secretKey;
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(env.getProperty("jwt.secret-key").getBytes());
    }
    
    public Map<String, String> getJwtFromCookie(Cookie cookies[]) {
    	Map<String, String> jwts = new HashMap<>();
		
		for (int i = 0; i < cookies.length; i++) {
			if(cookies[i].getName().equals(env.getProperty("jwt.name"))) {
				jwts.put(env.getProperty("jwt.name"), cookies[i].getValue());
			}
			
			if(cookies[i].getName().equals(env.getProperty("jwt.refresh-name"))) {
				jwts.put(env.getProperty("jwt.refresh-name"), cookies[i].getValue());
			}
		}
    	return jwts;
    }
    
    public String generateJwt(String email, String role) {
    	// 10분
        long tokenPeriod = 1000L * 60L * 10L;
        
        // 10 초
        // long tokenPeriod = 1000L * 10L;
        
        Map<String, Object> claims = new HashMap<>();
        
        Date now = new Date();
        
        claims.put("role", role);
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Token generateJwtAndRefresh(String email, String role) {
    	// 10분
        long tokenPeriod = 1000L * 60L * 10L;
        
        // 10 초
//        long tokenPeriod = 1000L * 10L;
        
        // 3주
        long refreshPeriod = 1000L * 60L * 60L * 24L * 21L;
        
        // 30 초
//        long refreshPeriod = 1000L * 30L;

        Map<String, Object> claims = new HashMap<>();
        
        Date now = new Date();
        
        claims.put("role", role);
        claims.put("email", email);

        return new Token(
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(), 
            Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact());
    }

    public boolean isExpired(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);

            return claims.getBody()
                    .getExpiration()
                    .before(new Date());
            
        } catch (Exception e) {
            return true;
        }
    }

    public Map<String, Object> getJwtBody(String token) {
        return Jwts.parser()
        		.setSigningKey(secretKey)
        		.parseClaimsJws(token)
        		.getBody();
    }
    
    public String getEmail(String token) {
    	return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("email").toString();
    }
}