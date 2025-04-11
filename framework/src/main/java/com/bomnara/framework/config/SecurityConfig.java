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
import com.bomnara.framework.service.impl.LoginServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	private final LoginServiceImpl loginservice;
	private final CustomAuthenticationFailureHandler customFailureHandler;
	
	//http://localhost:8080/swagger-ui/index.html
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("BOMNARA API")
                .description("BOMNARA API reference for developers")
                .version("1.0");
    }
	
	// 회원 가입시 패스워드 처리
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
			.authorizeHttpRequests((authorizeRequest) ->
						authorizeRequest
							.requestMatchers(PathRequest.toH2Console()).permitAll()
							.requestMatchers( "/css/**", "/img/**", "/js/**", "/scss/**", "/vendor/**","/fragment/**").permitAll()
							.requestMatchers("/","/login","/loginProc","/register","/registerProc","/unauthorized").permitAll()
							.requestMatchers("/swagger-ui/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
							.requestMatchers("/api/token**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())							
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
	
	/**
	 * 401 처리
	 */
	private final AuthenticationEntryPoint unauthorizedEntryPoint =
            (request, response, authException) -> {		                
            	// page 와 api 분기 해서 처리
            	if(request.getRequestURI().indexOf("/api")>-1){
            		response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    String json = new ObjectMapper().writeValueAsString(HttpStatus.UNAUTHORIZED);
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    PrintWriter writer = response.getWriter();
                    writer.write(json);
                    writer.flush();
            	}else {
            		response.sendRedirect("/unauthorized");
            	}
            	
            };
            
           
    /**
     * 403처리
     */
    private final AccessDeniedHandler accessDeniedHandler =
            (request, response, accessDeniedException) -> {		                
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(HttpStatus.FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

}