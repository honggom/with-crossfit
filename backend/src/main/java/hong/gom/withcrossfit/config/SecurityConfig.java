package hong.gom.withcrossfit.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import hong.gom.withcrossfit.jwt.CookieUtils;
import hong.gom.withcrossfit.jwt.JwtAuthenticationFilter;
import hong.gom.withcrossfit.jwt.TokenUtils;
import hong.gom.withcrossfit.service.SpOAuth2UserService;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    private final SpOAuth2SuccessHandler successHandler;
    private final SpOAuth2UserService oAuth2UserService;
    private final TokenUtils tokenUtils;
    private final Environment env;
    private final CookieUtils cookieUtils;
    
    // TODO 토큰없이 접근시 예외처리
    // https://fenderist.tistory.com/344
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
            .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).and()
                .oauth2Login()
                .successHandler(successHandler)
                .userInfoEndpoint().userService(oAuth2UserService)
                ;
        
        http.addFilterBefore(new JwtAuthenticationFilter(tokenUtils, cookieUtils, env), UsernamePasswordAuthenticationFilter.class);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    
}