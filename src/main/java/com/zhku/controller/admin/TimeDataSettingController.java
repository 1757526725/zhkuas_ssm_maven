package com.zhku.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/time")
public class TimeDataSettingController {
	@RequestMapping("")
	public String show(){
		
		return "admin_time_setting";
	}
}
