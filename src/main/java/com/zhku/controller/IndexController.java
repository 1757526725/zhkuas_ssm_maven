package com.zhku.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.service.db.ITermService;

@Controller
public class IndexController {
	
	@Autowired
	private ITermService termService;
	@RequestMapping(value={"","/index","/main","/main/index"})
	public String index(HttpServletRequest request,HttpSession session){
		return "index";
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
