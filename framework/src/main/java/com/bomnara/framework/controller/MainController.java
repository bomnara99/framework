package com.bomnara.framework.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/**
 * MAIN
 */

@Log4j2
@Controller
public class MainController {
	

	/**
	 * 메인 화면
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/main")
	public String main(Model model,Authentication authentication) {

		model.addAttribute("userId", authentication.getName()); 
		
		return "t_main.html";
	}
	
	
}
