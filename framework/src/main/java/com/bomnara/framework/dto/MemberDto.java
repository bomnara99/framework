package com.bomnara.framework.dto;


import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberDto {


    private String userId;

    private String pwd;

    @Builder
    public MemberDto(String userId, String pwd) {
        this.userId = userId;
        this.pwd = pwd;
    }

    public Member toEntity(){
    	Member member = Member.builder()
        .userId(userId)
        .pwd(pwd)
        .role(Role.USER)
        .build();
        return member;
    }
}