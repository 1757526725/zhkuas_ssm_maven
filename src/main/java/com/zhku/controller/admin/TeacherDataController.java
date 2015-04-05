package com.zhku.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.Teacher;
import com.zhku.service.db.ITeacherService;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("/admin/school")
public class TeacherDataController {

	@Autowired
	private ITeacherService teacherService;
	@RequestMapping("/teachers")
	public String show(HttpServletRequest request){
		List<Teacher> teachers = teacherService.getTeachers(1, PageSize.COMMON_SIZE.getSize(), true);
		request.setAttribute("teachers", teachers);
		return "admin_teacher";
	}
	@RequestMapping("/teachers/page/{pageNum}")
	public String show(@PathVariable Integer pageNum,HttpServletRequest request){
		List<Teacher> teachers = teacherService.getTeachers(pageNum, PageSize.COMMON_SIZE.getSize(), true);
		request.setAttribute("teachers", teachers);
		return "admin_teacher";
	}
	public ITeacherService getTeacherService() {
		return teacherService;
	}
	public void setTeacherService(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}

	

}
