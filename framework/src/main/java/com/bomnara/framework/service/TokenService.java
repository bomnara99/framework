package com.bomnara.framework.service;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import com.bomnara.framework.domain.Token;

public interface TokenService {
	
	Optional<Token> getGenerateToken(Authentication authentication); 
	Page<Token> getTokenList(Authentication authentication, Pageable pageable);
	
}
