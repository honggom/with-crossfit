package hong.gom.withcrossfit.jwt;

import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final TokenUtils tokenUtils;

	private final CookieUtils cookieUtils;

	private final Environment env;

	private RequestMatcher requestMatcher = new AntPathRequestMatcher("/api/**");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {

			logger.info("JwtAuthFilter 진입 ====================================");

			Map<String, String> jwts = tokenUtils.getJwtFromCookie(((HttpServletRequest) request).getCookies());

			String jwt = jwts.get(env.getProperty("jwt.name"));
			String refresh = jwts.get(env.getProperty("jwt.refresh-name"));

			// jwt 기간 만료
			if (tokenUtils.isExpired(jwt)) {

				System.out.println("jwt 만료");

				// ref 기간 유효
				if (!tokenUtils.isExpired(refresh)) {
					Map<String, Object> refreshJwtClaims = tokenUtils.getJwtBody(refresh);

					String refreshJwtEmail = refreshJwtClaims.get("email").toString();
					String newJwt = tokenUtils.generateJwt(refreshJwtEmail, refreshJwtClaims.get("role").toString());

					cookieUtils.addJwtCookie((HttpServletResponse) response, newJwt);

					authenticated(refreshJwtEmail);
					filterChain.doFilter(request, response);

					// jwt, ref 둘다 기간 만료
				} else {

					System.out.println("ref 만료");

					((HttpServletResponse) response).sendRedirect(env.getProperty("front-end.base-url") + "/logout");
//				request.getRequestDispatcher(env.getProperty("front-end.base-url") + "/logout").forward(request,
//						response);
					return;
				}
			}
			Map<String, Object> jwtClaims = tokenUtils.getJwtBody(jwt);
			authenticated(jwtClaims.get("email").toString());
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			System.out.println("예외 발생");
			response.sendRedirect(env.getProperty("front-end.base-url") + "/logout");
//			request.getRequestDispatcher(env.getProperty("front-end.base-url") + "/logout").forward(request,
//					response);
			filterChain.doFilter(request, response);
		}
	}

	// TODO 권한 처리
	private void authenticated(String email) {
		Authentication auth = new UsernamePasswordAuthenticationToken(email, "",
				List.of(new SimpleGrantedAuthority("ROLE_USER")));

		SecurityContextHolder.getContext().setAuthentication(auth);
	}

}