package hong.gom.withcrossfit.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import hong.gom.withcrossfit.jwt.CookieUtils;
import hong.gom.withcrossfit.jwt.TokenUtils;
import lombok.RequiredArgsConstructor;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int ERROR_FORBIDDEN = 403;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		logger.info("쿠키가 없거나 토큰이 만료됨");
		setResponse(response);
		return;
	}

	private void setResponse(HttpServletResponse response) throws IOException {
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().print("{ \"error\":"  + ERROR_FORBIDDEN + "}");
	}
}
