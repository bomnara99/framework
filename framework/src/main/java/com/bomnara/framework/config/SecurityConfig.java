package com.bomnara.framework.config;

import java.io.PrintWriter;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.bomnara.framework.Exception.CustomAuthenticationFailureHandler;
import com.bomnara.framework.domain.Role;
import com.bomnara.framework.service.impl.LoginserviceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final LoginserviceImpl loginservice;
	private final CustomAuthenticationFailureHandler customFailureHandler;

	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		return http
			.csrf(
					(csrfConfig) -> csrfConfig.disable()
					) 
			.headers(
					(headerConfig) -> headerConfig.frameOptions(
							frameOptionConfig ->  frameOptionConfig.disable()
						)
					)
			.authorizeRequests((authorizeRequest) ->
						authorizeRequest
							.requestMatchers(PathRequest.toH2Console()).permitAll()
							.requestMatchers( "/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**").permitAll()
							.requestMatchers("/","/login","/loginProc","/register","/registerProc","/unauthorized").permitAll()
							.requestMatchers("/api/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())							
							.requestMatchers("/admin/**").hasRole(Role.ADMIN.name())
							.anyRequest().authenticated()
					)
			.formLogin((formLogin) ->
						formLogin
							.loginPage("/login")
							.usernameParameter("userId")
							.passwordParameter("pwd")
							.loginProcessingUrl("/loginProc")
							.defaultSuccessUrl("/main",true)
							.failureHandler(customFailureHandler)
					)
//			.logout((logoutConfig) ->
//						logoutConfig.logoutSuccessUrl("/")
//					)
			.userDetailsService(loginservice)
			.exceptionHandling((exceptionConfig) ->
            	exceptionConfig.authenticationEntryPoint(unauthorizedEntryPoint).accessDeniedHandler(accessDeniedHandler)
				) // 401 403 관련 예외처리
			.build();
			
		
	}
	
	private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {		                
            	response.sendRedirect("/unauthorized");
            };

    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {		                
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(HttpStatus.FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };
	
    @Getter
    @RequiredArgsConstructor
    public class ErrorResponse {

        private final HttpStatus status;
        private final String message;
    }
}
