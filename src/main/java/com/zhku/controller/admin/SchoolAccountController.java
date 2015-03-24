package com.zhku.controller.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.User;
import com.zhku.service.db.IUserService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("/admin")
public class SchoolAccountController {

	@Autowired
	private IUserService userService;
	@RequestMapping(value="/account/users")
	public String show(HttpServletRequest request){
		List<User> users = userService.getUsers(1, PageSize.ADMIN_SCHOOL_USER_LIST.getSize(),true);
		request.setAttribute("users", users);
		return "admin_school_account";
	}
	@RequestMapping(value="/account/users/page/{pageNum}")
	public String show(@PathVariable Integer pageNum,HttpServletRequest request){
		List<User> users = userService.getUsers(pageNum, PageSize.ADMIN_SCHOOL_USER_LIST.getSize(),true);
		request.setAttribute("users", users);
		return "admin_school_account";
	}
	
	@RequestMapping(value="/account/users",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> find(String keyword){
		List<User> users = userService.findUserByKeyword(keyword);
		return  WebUtils.webJsonResult(users);
	}
	
	@RequestMapping(value="/account/users",method=RequestMethod.PUT)
	@ResponseBody
	public Map<String,Object> unBind(HttpServletRequest request){
		String [] uids =request.getParameterValues("uid[]");
		try{
			List<User> users = new ArrayList<User>();
			for(String strId:uids){
				int id = Integer.valueOf(strId);
				User user = userService.getUserById(id);
				if(user==null){
					return WebUtils.webJsonError("没有找到uid为"+id+"的用户!");
				}
				if(user.getStudent()!=null){
					user.getStudent().setsNo(null);
					userService.updateUser(user);
					user.setStudent(null);
					users.add(user);
				}
			}
			return WebUtils.webJsonResult(users);
		}catch (Exception e) {
			return WebUtils.webJsonError(Error.PARAMETER_ERROR);
		}
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
