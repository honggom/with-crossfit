package hong.gom.withcrossfit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import hong.gom.withcrossfit.entity.SpOAuth2User;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.Token;
import hong.gom.withcrossfit.jwt.TokenService;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler  {

	private final TokenService tokenService;
	private final SpUserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("나 success");
		
		Object principal = authentication.getPrincipal();

		if (principal instanceof OidcUser) {
			SpOAuth2User oauth = SpOAuth2User.Provider.google.convert((OidcUser) principal);
			SpUser user = userService.load(oauth);
			SecurityContextHolder.getContext()
					.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
		}
		System.out.println("oauth2 성공");
		System.out.println("oauth2 성공");
		System.out.println("oauth2 성공");

		// TODO "USER" 서버에서 롤 가져와야 됨
		Token token = tokenService.generateToken(authentication.getName(), "USER");
		System.out.println(token);
		System.out.println(token);
		Cookie jwtCookie = new Cookie("jwt", token.getToken());
		jwtCookie.setPath("http://localhost:3000/index.html");
		
		// 쿠키 저장 후 프론트 페이지로
		response.addCookie(jwtCookie);
		getRedirectStrategy().sendRedirect(request, response, "http://localhost:3000/index.html");
	}

}