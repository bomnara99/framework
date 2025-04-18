package com.bomnara.framework.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bomnara.framework.domain.Member;
import com.bomnara.framework.domain.Role;
import com.bomnara.framework.service.LoginService;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

/**
 * LOGIN 
 */
@Log4j2
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * INDEX
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String home() {
	    return "t_index.html";
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
	 * 회원가입
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/register")
	public String register(Model model) {		
		log.info("register page 시작");
		
		return "t_register.html";
	}
	
	/**
	 * 회원가입 처리
	 * 
	 * @param model
	 * @return
	 */
	@Hidden
	@ResponseBody
	@RequestMapping(value="/registerProc",method=RequestMethod.POST)
	public HashMap<String,Object> registerProc(@RequestParam(value="userId",required=true) String userId
			, @RequestParam(value="pwd",required=true) String pwd) {		
		log.info("register proc 시작");
		Member member = new Member(userId,pwd,Role.USER);		
		HashMap<String,Object> returnData = loginService.registerProc(member);
		
		
		return returnData;
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
