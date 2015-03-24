package com.zhku.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/user")
public class MyPublicCourseController {

	@RequestMapping("/course/statistics")
	public String show(){
		
		return "myPublicCourse";
	}
}
