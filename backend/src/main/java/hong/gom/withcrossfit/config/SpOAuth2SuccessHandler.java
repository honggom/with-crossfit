package hong.gom.withcrossfit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.entity.SpAuthority;
import hong.gom.withcrossfit.entity.SpOAuth2User;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.CookieUtils;
import hong.gom.withcrossfit.jwt.Token;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SpOAuth2SuccessHandler implements AuthenticationSuccessHandler  {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final TokenUtils tokenUtils;
	
	private final SpUserService userService;
	
	private final CookieUtils cookieUtils;
	
	@Autowired
	private Environment env;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("AuthenticationSuccessHandler 진입 ====================================");

		Object principal = authentication.getPrincipal();

		if (principal instanceof OidcUser) {
			SpOAuth2User oauth = SpOAuth2User.Provider.google.convert((OidcUser) principal);
			SpUser user = userService.load(oauth);
			
			// TODO 관리자를 지정하는 방법 구상..
			// 1. 슈퍼관리자가 관리자를 지정 (슈퍼관리자는 '나')
			// 2. 아이디를 미리 만들어 놓고 제공 (구글로 하는게 의미가 없음)
			
			// TODO 여기서 만약 관리자의 가입 처리가 안 됐다면 걸러줘야됨
			SpAuthority adminRole = new SpAuthority(user.getUserId(), "ROLE_ADMIN");
	    	if (user.getAuthorities().contains(adminRole)) {
				Token adminToken = tokenUtils.generateJwtAndRefresh(user.getEmail(), "ROLE_ADMIN");
				cookieUtils.addCookies(response, adminToken);
				response.sendRedirect(env.getProperty("front-end-admin.base-url") + "/home");
				return;
	    	}
			
			// TODO "USER" 서버에서 롤 가져와야 됨
			Token userToken = tokenUtils.generateJwtAndRefresh(user.getEmail(), "ROLE_USER");
			cookieUtils.addCookies(response, userToken);
		}
		response.sendRedirect(env.getProperty("front-end.base-url") + "/home");
	}
	
}