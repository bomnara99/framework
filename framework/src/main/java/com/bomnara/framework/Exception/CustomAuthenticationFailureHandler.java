package com.bomnara.framework.Exception;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{
	
	 @Override
	    public void onAuthenticationFailure(HttpServletRequest request,
	                                        HttpServletResponse response,
	                                        AuthenticationException exception) throws IOException, ServletException {
		 
		 request.getSession().setAttribute("errorMsg", exception.getMessage());
		 response.sendRedirect("/login?error");
		 
	    }
	 
}

