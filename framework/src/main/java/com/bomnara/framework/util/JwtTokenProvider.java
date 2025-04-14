package com.bomnara.framework.util;

import com.bomnara.framework.domain.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    @Value("${jwt.secretKey}")
    private String secretKey;

    private Key key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private final long tokenValidityInMillis = 1000 * 60 * 60 * 24; // 1일



    // 토큰 생성
    public Token createToken(Authentication authentication) {
        Token jwtToken = new Token();
        //권한 정보
        String authorities =  authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        Date now = new Date();
        Date expiry = new Date(now.getTime() + tokenValidityInMillis);

        String jwtTokenStr = Jwts.builder()
                .subject(authentication.getName())
                .claim("auth", authorities)
                .claim("userId", authentication.getName())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();

        jwtToken.setToken(jwtTokenStr);
        jwtToken.setUserId(authentication.getName());
        jwtToken.setExpiredDate(expiry);

        return jwtToken;
    }

    // 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        String username = getClaims(token).getSubject();
        var userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            getClaims(token); // 예외 안 나면 유효
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 클레임 추출
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // HTTP 헤더에서 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader("Authorization");
        if (bearer != null && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
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
