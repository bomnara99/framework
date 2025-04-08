package com.bomnara.framework.service;
import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;

import com.bomnara.framework.domain.Member;

public interface LoginService {
	
	HashMap<String,Object> registerProc(Member member); 
	
	
}
