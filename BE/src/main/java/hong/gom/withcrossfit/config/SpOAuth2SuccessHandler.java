package hong.gom.withcrossfit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.entity.SpOAuth2User;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.Token;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpOAuth2SuccessHandler implements AuthenticationSuccessHandler  {

	private final TokenUtils tokenUtils;
	private final SpUserService userService;
	
	@Autowired
	private Environment env;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		System.out.println("success filter 진입 ====================================");

		Object principal = authentication.getPrincipal();

		if (principal instanceof OidcUser) {
			SpOAuth2User oauth = SpOAuth2User.Provider.google.convert((OidcUser) principal);
			SpUser user = userService.load(oauth);
			System.out.println("oauth2 인증 성공 user : " + user.toString());

			// TODO "USER" 서버에서 롤 가져와야 됨
			Token token = tokenUtils.generateToken(user.getEmail(), "USER");
			addCookies(response, token);
			System.out.println("토큰 추가");
		}
		response.sendRedirect(env.getProperty("front-end.base-url") + "/index.html");
	}

	private void addCookies(HttpServletResponse response, Token token) {
		Cookie jwt = new Cookie("jwt", token.getToken());
		Cookie refreshJwt = new Cookie("refreshJwt", token.getRefreshToken());

		jwt.setPath("/");
		refreshJwt.setPath("/");

		response.addCookie(jwt);
		response.addCookie(refreshJwt);
	}

}