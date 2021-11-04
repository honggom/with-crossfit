package hong.gom.withcrossfit.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CookieUtils {
	
	private final Environment env;
	
	public void addJwtCookie(HttpServletResponse response, String jwt) {
		Cookie jwtCookie = new Cookie(env.getProperty("jwt.name"), jwt);
		
		jwtCookie.setPath("/");
		
		response.addCookie(jwtCookie);
	}
	
	public void addCookies(HttpServletResponse response, Token token) {
		Cookie jwt = new Cookie(env.getProperty("jwt.name"), token.getJwt());
		Cookie refresh = new Cookie(env.getProperty("jwt.refresh-name"), token.getRefresh());

		jwt.setPath("/");
		refresh.setPath("/");

		response.addCookie(jwt);
		response.addCookie(refresh);
	}

}
