package com.zhku.controller.util;

import javax.servlet.http.HttpSession;

import com.zhku.bean.User;
import com.zhku.web.Constants.SessionKey;

public class SecurityUtil {

	public static boolean checkLogin(HttpSession session){
		if(session.getAttribute(SessionKey.LOGIN_USER.toString())!=null){
			return true;
		}
		return false;
	}

	public static User getUser(HttpSession session){
		Object userObj = session.getAttribute(SessionKey.LOGIN_USER.toString());
		if(userObj!=null){
			return (User) userObj;
		}
		return null;
	}
}
