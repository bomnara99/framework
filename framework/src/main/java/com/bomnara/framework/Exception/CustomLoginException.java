package com.bomnara.framework.Exception;

import org.springframework.security.core.AuthenticationException;

import com.bomnara.framework.domain.ReturnCode;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginException extends AuthenticationException{
	
	public CustomLoginException(ReturnCode returnCode) {
		super(returnCode.getMessage());
		log.info("[ returnCode : " + returnCode.getReturnCode()+" || ErrorMsg : "+ returnCode.getMessage()+"]");
    }
	
}
