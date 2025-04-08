package com.bomnara.framework.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bomnara.framework.service.TokenService;

import lombok.extern.log4j.Log4j2;

/**
 * TOKEN
 */
@Log4j2
@Controller
@RequestMapping("/token")
public class TokenController {
	
	@Autowired
	private TokenService tokenService;
		
	/**
	 * TOKEN 생성
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/generateToken",method=RequestMethod.POST)
	public HashMap<String,Object> registerProc(Authentication authentication) {		
		log.info("register proc 시작");
		HashMap<String,Object> returnData = tokenService.generateToken(authentication);
		
		
		return returnData;
	}
	
}
