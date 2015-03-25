package com.zhku.web.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhku.bean.Term;
import com.zhku.service.db.ITermService;
/**
 * 检查顶部的数据是否显示完全，否则自动填充数据
 * @author JackCan
 *
 */
public class TopBarDataCheckInterceptor implements HandlerInterceptor{
	
	@Autowired
	private ITermService termService;
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception e) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView mode) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		HttpSession session = request.getSession(true);  
		if(session.getAttribute("terms")==null){
			List<Term> terms = termService.getAvailabelTerms();
			session.setAttribute("terms", terms);
			for(Term term:terms){
				if(term.isCurrent()){
					session.setAttribute("term", term);
				}
			}
		}
        return true;  
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
