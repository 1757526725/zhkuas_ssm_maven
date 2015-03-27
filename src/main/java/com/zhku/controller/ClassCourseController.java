package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.BaseClass;
import com.zhku.service.db.IBaseClassService;

@Controller
@RequestMapping("/main/class")
public class ClassCourseController {

	@Autowired
	private IBaseClassService baseclassService;
	@RequestMapping(value={"","/course"})
	public String show(HttpSession session,HttpServletRequest request){
		List<BaseClass> classess = baseclassService.getBaseClasses();
		request.setAttribute("classes",classess);
		return "class_course";
	}
	@RequestMapping("/{classNo}/course")
	public String show(@PathVariable String classNo,HttpSession session,HttpServletRequest request){
		List<BaseClass> classess = baseclassService.getBaseClasses();
		BaseClass clazz = baseclassService.getBaseClassByClassNo(classNo);
		request.setAttribute("classes",classess);
		request.setAttribute("clazz",clazz);
		return "class_course";
	}
	public IBaseClassService getBaseclassService() {
		return baseclassService;
	}
	public void setBaseclassService(IBaseClassService baseclassService) {
		this.baseclassService = baseclassService;
	}
	
	
}
