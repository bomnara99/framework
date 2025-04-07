package com.bomnara.framework.util;

public class BomUtil {

	public static boolean isValidEmail(String email) {
		// 이메일 유효성 검사 정규표현식
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		return email.matches(emailRegex);
	}

}
