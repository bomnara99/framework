package com.bomnara.framework.util;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.bomnara.framework.domain.Token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenUtil {
	
	@Value("${jwt.secretKey}")
	private String secretKey;
	
	private static long EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 1일
	

	public Token generateToken(Authentication authentication) {
		Token jwtToken = new Token();
		//권한 정보
		String authorities =  authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		
		Date now = new Date();
	    Date accessExpiration = new Date(now.getTime() + EXPIRATION_TIME);
			    
		String jwtTokenStr = Jwts.builder()
	            .subject(authentication.getName())
	            .claim("auth", authorities)
	            .claim("userId", authentication.getName())
	            .issuedAt(now)
	            .expiration(accessExpiration)
	            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), Jwts.SIG.HS256)
	            .compact();
		
		jwtToken.setToken(jwtTokenStr);
		jwtToken.setUserId(authentication.getName());
		jwtToken.setExpiredDate(accessExpiration);
		
		return jwtToken;
	}

}
