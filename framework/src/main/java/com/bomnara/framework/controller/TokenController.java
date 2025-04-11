package com.bomnara.framework.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bomnara.framework.domain.Token;
import com.bomnara.framework.service.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

/**
 * TOKEN
 */
@Log4j2
@RestController
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
	@Operation(summary = "TOKEN 생성",description = "로그인 정보의 userId와 Role을 이용하여 token 생성")
	public ResponseEntity<Token> getGenerateToken(Authentication authentication) {		
		
		log.info("TOKEN 생성 시작");
		Optional<Token> optionalToken  = tokenService.getGenerateToken(authentication);
		
		return optionalToken.map(ResponseEntity::ok) // 값이 있으면 200 OK + body
	            .orElseGet(() -> ResponseEntity.notFound().build()); // 없으면 404 Not Found
	}
	
	
	/**
	 * TOKEN 리스트 조회
	 * 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTokenList",method=RequestMethod.POST)
	@Operation(summary = "TOKEN 리스트 조회",description = "로그인 정보의 userId를 이용하여 리스트 조회 - h2 DB")
	public ResponseEntity<Page<Token>> getTokenList(Authentication authentication,
																@RequestParam(name = "pageNo") int pageNo,
																@RequestParam(name = "pageSize") int pageSize) {		
		
		log.info("TOKEN 리스트 조회 시작");
		
		Pageable pageable = PageRequest.of(pageNo-1, pageSize,Sort.by("expiredDate").descending());
		
		Page<Token> tokenList = tokenService.getTokenList(authentication,pageable);
		
		return ResponseEntity.ok(tokenList);
		
	}

	/**
	 * TOKEN 검증
	 *
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getTokenCheck",method=RequestMethod.POST)
	@Operation(summary = "TOKEN 체크",description = "생성된 토큰 체크")
	public ResponseEntity<String> getTokenCheck(@RequestParam(name = "token") String token) {

		log.info("TOKEN 체크 시작");
		String str  = tokenService.getTokenCheck(token);

		return ResponseEntity.ok(str);
	}
	
}
