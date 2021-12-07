package hong.gom.withcrossfit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final int UNAUTHENTICATED = 401;
	private static final int FORBIDDEN = 403;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		setResponse(response);
		return;
	}

	private void setResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		if (response.getStatus() == 403) {
			logger.info("접근 권한 없음");
			response.getWriter().print("{ \"error\":"  + FORBIDDEN + "}");
			return;
		} else {
			logger.info("쿠키가 없거나 토큰이 만료됨");
			response.getWriter().print("{ \"error\":"  + UNAUTHENTICATED + "}");
			return;
		}
	}
}