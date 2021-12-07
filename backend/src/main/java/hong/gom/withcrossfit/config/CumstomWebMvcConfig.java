package hong.gom.withcrossfit.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import hong.gom.withcrossfit.Interceptor.CustomInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CumstomWebMvcConfig implements WebMvcConfigurer {

	private final CustomInterceptor Interceptor;
	private final Environment env;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins(env.getProperty("front-end.base-url"), env.getProperty("front-end-admin.base-url"), env.getProperty("back-end.base-url"))
		        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
		        .allowedHeaders("*")
		        .allowCredentials(true)
		        ;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(Interceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
	}
}