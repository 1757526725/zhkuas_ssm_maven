package com.zhku.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/message")
public class ErrorUIController{
	@RequestMapping("/error")
	public String showMassage(HttpSession session){
		return "error";
	}
}
