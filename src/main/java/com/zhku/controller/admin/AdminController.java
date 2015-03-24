package com.zhku.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhku.bean.Admin;
import com.zhku.service.db.IAdminService;
import com.zhku.utils.ServiceUtils;
import com.zhku.web.Constants;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private IAdminService adminService;
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(String username,String password,HttpSession session,HttpServletRequest request){
		Admin admin  = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		//校验用户名密码
		if(admin.getUsername()==null||admin.getUsername().equals("")){
			request.setAttribute("result", "用户名不能为空!");
			return "admin_login";
		}else if(admin.getPassword()==null||admin.getPassword().equals("")){
			request.setAttribute("result", "密码不能为空!");
			return "admin_login";
		}
		
		//将password md5加密
		admin.setPassword(ServiceUtils.toMD5(admin.getPassword()));
		admin=adminService.findAdmin(admin);
		if(admin==null){
			request.setAttribute("result", "用户名或者密码错误!");
			return "admin_login";
		}
		session.setAttribute(Constants.SessionKey.ADMIN_USER.toString(), admin);
		return "redirect:index";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpSession session){
		Admin admin=(Admin) session.getAttribute(Constants.SessionKey.ADMIN_USER.toString());
		if(admin==null){
			return "admin_login";
		}
		return "redirect:index";
	}
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logout(HttpSession session){
		session.removeAttribute(Constants.SessionKey.ADMIN_USER.toString());
		return "redirect:login";
	}
	@RequestMapping(value="/index")
	public String home(HttpSession session){
		Admin admin=(Admin) session.getAttribute(Constants.SessionKey.ADMIN_USER.toString());
		if(admin==null){
			return "redirect:login";
		}
		return "admin_index";
	}
	public IAdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

}
