package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.service.db.ITermService;
import com.zhku.service.db.IUserService;
import com.zhku.web.Constants.SessionKey;

@Controller
public class IndexController {
	
	@Autowired
	private ITermService termService;
	@Autowired
	private IUserService userService;
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@RequestMapping(value={"","/index","/main","/main/index"})
	public String index(HttpServletRequest request,HttpSession session){
		List<Term> terms = termService.getAvailabelTerms();
		session.setAttribute("terms", terms);
		for(Term term:terms){
			if(term.isCurrent()){
				session.setAttribute("term", term);
			}
		}
//		User user= userService.findUserBySinaUid("1615929995");
//		session.setAttribute(SessionKey.LOGIN_USER.toString(), user);
		return "index";
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
