package com.bomnara.framework.dto;


import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.Role;

@Getter
public class MemberDto {


    private String username;

    private String password;

    @Builder
    public MemberDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Member toEntity(){
    	Member member = Member.builder()
        .username(username)
        .password(password)
        .role(Role.USER)
        .build();
        return member;
    }
}