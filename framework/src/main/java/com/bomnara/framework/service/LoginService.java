package com.bomnara.framework.service;
import org.springframework.security.core.userdetails.UserDetails;

public interface LoginService {
	UserDetails loadUserByUsername(String username) throws Exception ;
}
