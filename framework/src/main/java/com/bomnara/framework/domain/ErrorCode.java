package com.bomnara.framework.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	
	NOT_FOUND(404,"404","PAGE NOT FOUND")
    ,INTER_SERVER_ERROR(500,"500","Internal Server Error")
    ,LOGIN_EMAIL_VALIED(460,"L01","이메일 형식이 잘못되었습니다.")
    ,NO_SEARCH_MEMBER(461,"L02","로그인에 실패하였습니다.") // 회원정보없음
    ;
	
	private Object status;
    private String errorCode;
    private String message;
}
