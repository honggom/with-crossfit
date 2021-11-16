package hong.gom.withcrossfit.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminJwtAuthenticationFilter extends GenericFilterBean {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final TokenUtils tokenUtils;

	private final CookieUtils cookieUtils;

	private final Environment env;

	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/admin/api/**");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			if (requestMatcher.matches((HttpServletRequest) request)) {

				logger.info("AdminJwtAuthenticationFilter 진입 ====================================");

				Map<String, String> jwts = tokenUtils.getJwtFromCookie(((HttpServletRequest) request).getCookies());

				String jwt = jwts.get(env.getProperty("jwt.name"));
				String refresh = jwts.get(env.getProperty("jwt.refresh-name"));

				if (!tokenUtils.isExpired(jwt)) {
					logger.info("jwt 유효");

					Map<String, Object> jwtClaims = tokenUtils.getJwtBody(jwt);
					
					if (jwtClaims.get("role").toString().equals("ROLE_ADMIN")) {
						logger.info("ADMIN 권한 있음");
						authenticated(jwtClaims.get("email").toString(), jwtClaims.get("role").toString());
					} else {
						logger.info("ADMIN 권한 없음");
						((HttpServletResponse)response).setStatus(403);
					}
				} else {
					if (!tokenUtils.isExpired(refresh)) {
						logger.info("ref 유효");

						Map<String, Object> refreshJwtClaims = tokenUtils.getJwtBody(refresh);

						String refreshJwtEmail = refreshJwtClaims.get("email").toString();
						String newJwt = tokenUtils.generateJwt(refreshJwtEmail,
								refreshJwtClaims.get("role").toString());
						logger.info("jwt 갱신");

						cookieUtils.addJwtCookie((HttpServletResponse) response, newJwt);
						authenticated(refreshJwtEmail, refreshJwtClaims.get("role").toString());
					} else {
						logger.info("쿠키 둘다 만료");
						((HttpServletResponse)response).setStatus(401);
					}
				}
			}
		} catch (Exception e) {
			logger.info("쿠키가 존재하지 않음");
			((HttpServletResponse)response).setStatus(401);
		} finally {
			filterChain.doFilter(request, response);
		}
	}

	private void authenticated(String email, String role) {
		Authentication auth = new UsernamePasswordAuthenticationToken(email, "",
				List.of(new SimpleGrantedAuthority(role)));

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}