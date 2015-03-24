package com.zhku.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zhku.service.db.IUserService;

@Controller
@RequestMapping("/admin/user")
public class UserController {
	@Autowired
	private IUserService userService;
	/**
	 * 显示用户列表,分页处理。
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String show(HttpServletRequest request){
		userService.getUsers();
		return null;
	}
	/**
	 * 查找用户
	 * @param keyword
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/some",method=RequestMethod.POST)
	public String search(String keyword,HttpServletRequest request){
		
		return null;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
