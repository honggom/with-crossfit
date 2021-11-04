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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

	private final TokenUtils tokenUtils;

	private final CookieUtils cookieUtils;

	private final Environment env;

	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("JwtAuthFilter 진입 ====================================");

		if (requestMatcher.matches((HttpServletRequest) request)) {
			Map<String, String> jwts = tokenUtils.getJwtFromCookie(((HttpServletRequest) request).getCookies());

			String jwt = jwts.get("jwt");
			String refreshJwt = jwts.get("refreshJwt");

			System.out.println(tokenUtils.isExpired(jwt));

			// jwt 기간 만료
			if (tokenUtils.isExpired(jwt)) {
				System.out.println("jwt 만료됨");
				if (!tokenUtils.isExpired(refreshJwt)) {
					System.out.println("ref 살아있음");
					Map<String, Object> refreshJwtClaims = tokenUtils.getJwtBody(refreshJwt);
					String refreshJwtEmail = refreshJwtClaims.get("email").toString();
					
					String newJwt = tokenUtils.generateJwt(refreshJwtEmail, refreshJwtClaims.get("role").toString());

					cookieUtils.addJwtCookie((HttpServletResponse) response, newJwt);
					
					authenticated(refreshJwtEmail);
					
					System.out.println("재발급 완료");
					
					chain.doFilter(request, response);
				} else {
					((HttpServletResponse) response)
							.sendRedirect(env.getProperty("front-end.base-url") + "/index.html");
					return;
				}
			}
			Map<String, Object> jwtClaims = tokenUtils.getJwtBody(jwt);
			authenticated(jwtClaims.get("email").toString());

			chain.doFilter(request, response);
		}
	}

	// TODO 권한 처리
	private void authenticated(String email) {
		Authentication auth = new UsernamePasswordAuthenticationToken(email, "",
				List.of(new SimpleGrantedAuthority("ROLE_USER")));

		SecurityContextHolder.getContext().setAuthentication(auth);

		System.out.println(SecurityContextHolder.getContext());
	}

}