package com.bomnara.framework.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.bomnara.framework.domain.ReturnCode;
import com.bomnara.framework.domain.Token;
import com.bomnara.framework.repository.TokenRepository;
import com.bomnara.framework.service.TokenService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service	
public class TokenServiceImpl implements TokenService{
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1일

	private final TokenRepository tokenRepository;
	
	/**
	 *
	 */
	public HashMap<String,Object> generateToken(Authentication authentication){		
		HashMap<String,Object> returnData = new HashMap<String,Object>();
	
		//권한 정보
		String authorities =  authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		
//		Claims claims = Jwts.claims();
		
		Date now = new Date();
	    Date accessExpiration = new Date(now.getTime() + EXPIRATION_TIME);

	    String jwtToken = Jwts.builder()
	            .subject(authentication.getName())
	            .claim("auth", authorities)
	            .claim("userId", authentication.getName())
	            .issuedAt(now)
	            .expiration(accessExpiration)
	            .signWith(getKey(), Jwts.SIG.HS256)
	            .compact();
	    
	    Token token = new Token();
	    token.setToken(jwtToken);
	    token.setUserId(authentication.getName());
	    token.setExpiredDate(accessExpiration);
	    
	    tokenRepository.save(token);

		returnData.put("token", jwtToken);
		returnData.put("result_code", ReturnCode.SUCCESS.getReturnCode());
		returnData.put("result_message", ReturnCode.SUCCESS.getMessage());
		return returnData;
		
	}
	
	public SecretKey getKey() {
	    return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}
	
}
