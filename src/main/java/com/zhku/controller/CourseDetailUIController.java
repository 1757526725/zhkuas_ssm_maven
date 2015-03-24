package com.zhku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 课程详细信息页
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/main/course/detail")
public class CourseDetailUIController {
	
	@RequestMapping("{cNo}")
	public String showDetail(@PathVariable String cNo){
		return "public_course_detail";
	}
}
