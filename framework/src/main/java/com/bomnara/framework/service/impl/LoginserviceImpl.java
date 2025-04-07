package com.bomnara.framework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bomnara.framework.Exception.CustomLoginException;
import com.bomnara.framework.domain.ErrorCode;
import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.MemberRepository;
import com.bomnara.framework.service.LoginService;
import com.bomnara.framework.util.BomUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service	
public class LoginserviceImpl implements LoginService,UserDetailsService {
	
//	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	private final MemberRepository memberRepository;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// email 체크
		if (!BomUtil.isValidEmail(username))	
			throw new CustomLoginException(ErrorCode.LOGIN_EMAIL_VALIED);		
		
		// 저장된 회원 정보 체크
		Member member = memberRepository.findByUsername(username).orElseThrow(
				() -> new CustomLoginException(ErrorCode.NO_SEARCH_MEMBER));
		
		// 권한 생성
		UserDetails userDetails = 
				User.builder().username(member.getUsername()).password(member.getPassword()).roles(member.getRole().name()).build();
		
        return userDetails;
    }
	
}
