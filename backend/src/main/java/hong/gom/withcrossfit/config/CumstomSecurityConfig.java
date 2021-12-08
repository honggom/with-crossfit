package hong.gom.withcrossfit.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import hong.gom.withcrossfit.jwt.AdminJwtAuthenticationFilter;
import hong.gom.withcrossfit.jwt.CookieUtil;
import hong.gom.withcrossfit.jwt.TokenUtil;
import hong.gom.withcrossfit.jwt.UserJwtAuthenticationFilter;
import hong.gom.withcrossfit.service.SpOAuth2UserService;
import lombok.RequiredArgsConstructor;

@Profile("!default")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class CumstomSecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final CustomAuthenticationSuccessHandler successHandler;
    private final SpOAuth2UserService oAuth2UserService;
    private final TokenUtil tokenUtils;
    private final Environment env;
    private final CookieUtil cookieUtils;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
        		.disable()
        		.cors()
        	.and()
                .csrf()
                	.disable()
                .sessionManagement()
                	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            	.addFilterBefore(new AdminJwtAuthenticationFilter(tokenUtils, cookieUtils, env), UsernamePasswordAuthenticationFilter.class)
            	.addFilterBefore(new UserJwtAuthenticationFilter(tokenUtils, cookieUtils, env), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                	.permitAll()
                .anyRequest()
                	.authenticated()
            .and()
            	.exceptionHandling()
            	.authenticationEntryPoint(new CustomAuthenticationEntryPoint())
            .and()
                .oauth2Login()
                	.successHandler(successHandler)
                .userInfoEndpoint()
                	.userService(oAuth2UserService)
                ;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
}