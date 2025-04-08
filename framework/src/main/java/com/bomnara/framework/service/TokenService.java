package com.bomnara.framework.service;
import java.util.HashMap;

import org.springframework.security.core.Authentication;

public interface TokenService {
	
	HashMap<String,Object> generateToken(Authentication authentication); 
	
	
}
