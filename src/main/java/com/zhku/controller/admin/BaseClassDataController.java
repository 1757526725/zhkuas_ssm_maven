package com.zhku.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.BaseClass;
import com.zhku.service.db.IBaseClassService;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("/admin")
public class BaseClassDataController {
	@Autowired
	private IBaseClassService baseClassService;
	@RequestMapping(value="/classes")
	public String show(HttpSession session ,HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClasses(1, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		return "admin_class";
	}
	@RequestMapping(value="/classes/page/{pageNum}")
	public String showPage(@PathVariable Integer pageNum ,HttpSession session ,HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClasses(pageNum, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		return "admin_class";
	}
	
	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}
	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
}
