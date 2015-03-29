package com.zhku.controller.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.Student;
import com.zhku.service.db.IStudentService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("admin/school/")
public class StudentDataController {

	@Autowired
	private IStudentService studentService;
	@RequestMapping("students")
	public String show(HttpServletRequest request){
		List<Student> students = studentService.getStudents(1,PageSize.COMMON_SIZE.getSize(),true);
		request.setAttribute("students", students);
		return "admin_student";
	}
	@RequestMapping("students/page/{pageNum}")
	public String show(@PathVariable Integer pageNum ,HttpServletRequest request){
		List<Student> students =studentService.getStudents(pageNum,PageSize.COMMON_SIZE.getSize(),true);
		request.setAttribute("students", students);
		return "admin_student";
	}
	//根据关键字查找学生信息 POST 需要管理员权限
	@ResponseBody
	@RequestMapping(value="students/json",method = RequestMethod.POST)
	public Map<String,Object> findStudent(String keyword){
		List<Student> students = studentService.getStudentsByKeyword(keyword);
		return WebUtils.webJsonResult(students);
	}
	public IStudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}
}
