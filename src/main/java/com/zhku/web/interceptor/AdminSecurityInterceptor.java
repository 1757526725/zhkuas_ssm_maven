package com.zhku.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.zhku.web.Constants;

public class AdminSecurityInterceptor implements HandlerInterceptor{

	private static final String LOGIN_URL = "login";

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
        // 从session 里面获取用户名的信息  
        Object obj = session.getAttribute(Constants.SessionKey.ADMIN_USER.toString());  
        // 判断如果没有取到用户信息，就跳转到登陆页面，提示用户进行登陆  
        if (obj == null || "".equals(obj.toString())) {  
        	response.sendRedirect(request.getContextPath()+"/admin/"+LOGIN_URL);  
        	return false;
        }  
        return true;  
	}

}
