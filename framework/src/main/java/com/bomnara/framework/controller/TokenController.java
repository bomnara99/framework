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
@RequestMapping("/api/token")
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
	@RequestMapping(value="/getGenerateToken",method=RequestMethod.POST)
	public HashMap<String,Object> getGenerateToken(Authentication authentication) {		
		log.info("register proc 시작");
		HashMap<String,Object> returnData = tokenService.getGenerateToken(authentication);
		
		
		return returnData;
	}
	
	
	/**
	 * TOKEN 리스트 조회
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTokenList",method=RequestMethod.POST)
	public HashMap<String,Object> getTokenList(Authentication authentication) {		
		log.info("register proc 시작");
		HashMap<String,Object> returnData = tokenService.getTokenList(authentication);
		
		
		return returnData;
	}
}
