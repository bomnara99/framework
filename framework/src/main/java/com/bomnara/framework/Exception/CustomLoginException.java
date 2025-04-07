package com.bomnara.framework.Exception;

import org.springframework.security.core.AuthenticationException;

import com.bomnara.framework.domain.ErrorCode;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginException extends AuthenticationException{
	
	public CustomLoginException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		log.info("[ ErrorCode : " + errorCode.getErrorCode()+" || ErrorMsg : "+ errorCode.getMessage()+"]");
    }
	
}
