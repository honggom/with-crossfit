package hong.gom.withcrossfit.config;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import hong.gom.withcrossfit.entity.SpAuthority;
import hong.gom.withcrossfit.entity.SpOAuth2User;
import hong.gom.withcrossfit.entity.SpUser;
import hong.gom.withcrossfit.jwt.CookieUtil;
import hong.gom.withcrossfit.jwt.Token;
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.service.SpUserService;
import lombok.RequiredArgsConstructor;

@Profile("dev")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler  {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
	private final TokenUtil tokenUtils;
	private final SpUserService userService;
	private final CookieUtil cookieUtils;
	private final Environment env;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logging("CustomAuthenticationSuccessHandler 진입 ...");

		Object principal = authentication.getPrincipal();
		boolean isAdmin = false;
		String redirectUrl = "";

		if (principal instanceof OidcUser) {
			SpOAuth2User oauth = SpOAuth2User.Provider.google.convert((OidcUser) principal);
			SpUser user = userService.load(oauth);
			
			Set<SpAuthority> authorities = user.getAuthorities();
			
			for(SpAuthority authority : authorities) {
			    if(authority.getAuthority().equals("ROLE_ADMIN")) {
			    	isAdmin = true;
			    }
			}
			
			if (isAdmin) {
				if(user.getBox() == null) {
					logging("박스 미등록 관리자 로그인");
					redirectUrl = env.getProperty("front-end-admin.base-url") + "/not-registered";
				} else {
					logging(user.getBox().getName() + " 지점 관리자 로그인");
					Token adminToken = tokenUtils.generateJwtAndRefresh(user.getEmail(), "ROLE_ADMIN");
					cookieUtils.addCookies(response, adminToken);
					redirectUrl = env.getProperty("front-end-admin.base-url") + "/home";
				}
			} else {
				if(user.getBox() == null) {
					logging("미등록 유저 로그인");
					redirectUrl = env.getProperty("front-end.base-url") + "/not-registered";
				} else {
					logging("유저 로그인");
					Token userToken = tokenUtils.generateJwtAndRefresh(user.getEmail(), "ROLE_USER");
					cookieUtils.addCookies(response, userToken);
					redirectUrl = env.getProperty("front-end.base-url") + "/home";
				}
			}
		}
		response.sendRedirect(redirectUrl);
	}
	
	private void logging(String message) {
		LOGGER.info("CustomAuthenticationSuccessHandler INFO : " + message);
	}
}