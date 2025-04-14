package com.bomnara.framework.service.impl;

import com.bomnara.framework.domain.Token;
import com.bomnara.framework.repository.TokenRepository;
import com.bomnara.framework.service.TokenService;
import com.bomnara.framework.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service	
public class TokenServiceImpl implements TokenService{
	
	private final TokenRepository tokenRepository;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	/**
	 * 토큰 생성
	 */
	public Optional<Token> getGenerateToken(Authentication authentication){		
		
		Token jwtToken = jwtTokenProvider.createToken(authentication);
		
	    tokenRepository.save(jwtToken);

		return Optional.of(jwtToken);
		
	}
	
	
	/**
	 * 토큰 리스트 조회
	 */
	public Page<Token> getTokenList(Authentication authentication, Pageable pageable){		
		
		
		Page<Token> pageToken = tokenRepository.findByUserId(authentication.getName(),pageable);

		return pageToken;
		
	}

	/**
	 * 토큰 체크
	 */
	public String getTokenCheck(String token){
		String msg ="";


		msg = jwtTokenProvider.tokenCheck(token);


		return msg;

	}
	
	
}
