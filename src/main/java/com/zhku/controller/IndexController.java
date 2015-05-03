package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.Term;
import com.zhku.service.db.INoticeService;
import com.zhku.service.db.ITermService;

@Controller
public class IndexController {
	@Autowired
	private INoticeService noticeService;
	@Autowired
	private ITermService termService;
	@RequestMapping(value={"","/index","/main","/main/index"})
	public String index(HttpServletRequest request,HttpSession session){
		Term term =null;
		if(session.getAttribute("terms")==null){
			List<Term> terms = termService.getAvailabelTerms();
			session.setAttribute("terms", terms);
			for(Term perterm:terms){
				if(perterm.isCurrent()){
					term  = perterm;
					session.setAttribute("term", perterm);
				}
			}
		}
		if(term==null){
			term = termService.getCurrentTerm();
		}
		request.setAttribute("notice", noticeService.getNoticeByTermNo(term.getNo()));
		return "index";
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
	public INoticeService getNoticeService() {
		return noticeService;
	}
	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}
}
