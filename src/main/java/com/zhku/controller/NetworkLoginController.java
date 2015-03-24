package com.zhku.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.service.network.IWebLogin;
import com.zhku.service.network.impl.WebLogin;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.SessionKey;

@Controller()
@RequestMapping("/network")
public class NetworkLoginController {
	public static Logger logger = Logger.getLogger(NetworkLoginController.class
			.getName());
	@RequestMapping("/validateCode")
	public @ResponseBody String getValidateCode(HttpServletResponse response,HttpSession session) {
		IWebLogin webLogin = new WebLogin();
		response.setContentType("image/gif");
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(webLogin.getValidateCodeImg());
			session.setAttribute(Constants.SessionKey.VCODE_COOKIE.toString(), webLogin.getCookies());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.warning("输出验证码错误!" + e.getMessage());
		} catch (FetchTimeoutException fe) {
			logger.warning("抓取验证码错误!" + fe.getMessage());
		}finally{
			if(out!=null){
				try {
					out.flush();
				} catch (IOException e) {
				}finally{
					try {
						out.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public Map<String,Object> networkLogin(HttpSession session ,String username, String password,String vCode) {
		Map<String,Object> retval= new HashMap<String,Object>();
		//校验 用户名 密码 ，和验证码
		retval.put("state", 0);
		if(username==null||username.equals("")){
			retval.put("error", Constants.Error.USERNAME_NULL_ERROR.toMap());
		}else if(password==null||password.equals("")){
			retval.put("error", Constants.Error.PASSWORD_NULL_ERROR.toMap());
		}else if(vCode==null||vCode.equals("")){
			retval.put("error", Constants.Error.VALIDATECODE_NULL_ERROR.toMap());
		}else{
			String cookies = (String) session.getAttribute(SessionKey.VCODE_COOKIE.toString());
			IWebLogin webLogin = new WebLogin(cookies);
			try {
				webLogin.login(null,username, password, vCode);
				session.setAttribute(SessionKey.STUDENT_COOKIE.toString()+username, cookies);
				session.setAttribute(SessionKey.STUDENT_NO.toString(), username);
			} catch (LoginOutTimeException e) {
//				e.printStackTrace();
				return WebUtils.webJsonError(Constants.Error.LOGIN_OUT_TIME);
			} catch (ConnectionException e) {
//				e.printStackTrace();
				return WebUtils.webJsonError( Constants.Error.CONNECTION_ERROR);
			} catch (SnoOrPasswordIncorrectException e) {
				return WebUtils.webJsonError(  Constants.Error.USERNAME_OR_PASSWORD_ERROR);
			} catch (ValidateCodeIncorrectException e) {
				return WebUtils.webJsonError( Constants.Error.VALIDATECODE_ERROR);
			}
		}
		return WebUtils.webJsonResult( "登陆成功");
	}

}
