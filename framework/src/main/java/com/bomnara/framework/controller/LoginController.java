package com.bomnara.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class LoginController {
	
	
	/**
	 * 
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String home() {
	    return "t_index.html"; // Thymeleaf나 JSP 등 뷰 이름
	}
	
	/**
	 * 로그인 화면
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,Model model) {
		
		log.info("login page 시작");
		
		Object error = request.getSession().getAttribute("errorMsg");
	    if (error != null) {
	        model.addAttribute("errorMsg", error.toString());
	        request.getSession().removeAttribute("errorMsg"); // 한 번만 보여주고 제거
	    }	    
		return "t_login.html";
		
	}
	
	/**
	 * 메인
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main(Model model) {		
		return "t_main.html";
	}
	
	
	/**
	 * 권한 없음 화면
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(Model model) {		
		return "t_unauthorized.html";
	}
	
}
