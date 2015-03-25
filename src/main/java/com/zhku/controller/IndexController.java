package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.Term;
import com.zhku.service.db.ITermService;

@Controller
public class IndexController {
	
	@Autowired
	private ITermService termService;
	@RequestMapping(value={"","/index","/main","/main/index"})
	public String index(HttpServletRequest request,HttpSession session){
		if(session.getAttribute("terms")==null){
			List<Term> terms = termService.getAvailabelTerms();
			session.setAttribute("terms", terms);
			for(Term term:terms){
				if(term.isCurrent()){
					session.setAttribute("term", term);
				}
			}
		}
		return "index";
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
