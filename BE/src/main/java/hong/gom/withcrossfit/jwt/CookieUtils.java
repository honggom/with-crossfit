package hong.gom.withcrossfit.jwt;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
	
	public void addJwtCookie(HttpServletResponse response, String jwt) {
		Cookie jwtCookie = new Cookie("jwt", jwt);
		jwtCookie.setPath("/");
		response.addCookie(jwtCookie);
	}
	
	public void addCookies(HttpServletResponse response, Token token) {
		Cookie jwt = new Cookie("jwt", token.getToken());
		Cookie refreshJwt = new Cookie("refreshJwt", token.getRefreshToken());

		jwt.setPath("/");
		refreshJwt.setPath("/");

		response.addCookie(jwt);
		response.addCookie(refreshJwt);
	}

}
