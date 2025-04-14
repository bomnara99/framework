package com.bomnara.framework.util;

import com.bomnara.framework.domain.Token;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;


@Component
@Log4j2
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

	public String tokenCheck(String token){
		String chkStr="정상 입니다.";
		try {
			Jwts.parser()
					.verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
					.build()
					.parseSignedClaims(token);

		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
			return "Invalid JWT Token";
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
			return "Expired JWT Token";
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
			return "Unsupported JWT Token";
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
			return "JWT claims string is empty.";
		}
		return chkStr;

	}

}
