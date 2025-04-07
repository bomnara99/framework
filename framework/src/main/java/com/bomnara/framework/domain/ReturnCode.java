package com.bomnara.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
	
	NOT_FOUND(404,"404","PAGE NOT FOUND")
    ,INTER_SERVER_ERROR(500,"500","Internal Server Error")
    ,LOGIN_EMAIL_VALIED(460,"L01","이메일 형식이 잘못되었습니다.")
    ,NO_SEARCH_MEMBER(461,"L02","로그인에 실패하였습니다.") // 회원정보없음
    ,EXISTS_MEMBER(462,"R01","이미 등록된 회원정보입니다.") // 회원정보없음
    ,SUCCESS(200,"000","정상처리되었습니다.") // 회원정보없음
    ;
	
	private Object status;
    private String returnCode;
    private String message;
}
