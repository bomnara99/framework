package com.bomnara.framework.service.impl;

import java.util.HashMap;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bomnara.framework.Exception.CustomLoginException;
import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.ReturnCode;
import com.bomnara.framework.repository.MemberRepository;
import com.bomnara.framework.service.LoginService;
import com.bomnara.framework.util.BomUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service	
public class LoginServiceImpl implements LoginService,UserDetailsService {
	
	private final MemberRepository memberRepository;
	
	/**
	 *
	 */
	@Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		
		// email 체크
		if (!BomUtil.isValidEmail(userId))	
			throw new CustomLoginException(ReturnCode.LOGIN_EMAIL_VALIED);		
		
		// 저장된 회원 정보 체크
		Member member = memberRepository.findByUserId(userId).orElseThrow(
				() -> new CustomLoginException(ReturnCode.NO_SEARCH_MEMBER));
		
		// 권한 생성
		UserDetails userDetails = 
				User.builder().username(member.getUserId()).password(member.getPwd()).roles(member.getRole().name()).build();
		
        return userDetails;
    }
	
	/**
	 *
	 */
	public HashMap<String,Object> registerProc(Member member){		
		HashMap<String,Object> returnData = new HashMap<String,Object>();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		member.setPwd(passwordEncoder.encode(member.getPwd()));
		
		// email 체크
		if (!BomUtil.isValidEmail(member.getUserId()))	
			throw new CustomLoginException(ReturnCode.LOGIN_EMAIL_VALIED);		
				
		// 저장된 회원 정보 체크
		if(!memberRepository.findByUserId(member.getUserId()).isEmpty()) {
			returnData.put("result_code", ReturnCode.EXISTS_MEMBER.getReturnCode());
			returnData.put("result_message", ReturnCode.EXISTS_MEMBER.getMessage());
			return returnData;
		}
		member = memberRepository.save(member);
		
		returnData.put("result_code", ReturnCode.SUCCESS.getReturnCode());
		returnData.put("result_message", ReturnCode.SUCCESS.getMessage());
		return returnData;
		
		
	}
	
}
