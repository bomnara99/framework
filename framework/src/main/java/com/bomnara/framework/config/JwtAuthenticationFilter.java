package com.bomnara.framework.config;

import com.bomnara.framework.util.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        if (request.getRequestURI().startsWith("/api/saleData")) {

            try {
                // jwt 토큰값 깨내기.
                String jwt = jwtTokenProvider.resolveToken(request);

                if (StringUtils.isNotEmpty(jwt) && jwtTokenProvider.validateToken(jwt)) {
                    // 토큰에서 userId 추출. 정상으로 있는지 정도로 간단하게 체크
                    Claims claims = jwtTokenProvider.getClaims(jwt); //jwt에서 사용자 id를 꺼낸다.]
                    String userId = (String) claims.get("userId");
                    request.setAttribute("userId", userId);
                    chain.doFilter(request, response);
                } else {
                    if (StringUtils.isEmpty(jwt)) {
                        // HttpStatus 401 처리
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }else if (jwtTokenProvider.validateToken(jwt)) {
                        // HttpStatus 403 처리
                        response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    }
                }
            } catch (Exception ex) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
            }

        }else{
            chain.doFilter(request, response);
        }

    }
}
