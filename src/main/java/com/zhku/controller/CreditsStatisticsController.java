package com.zhku.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 学分统计
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/main")
public class CreditsStatisticsController {

	@RequestMapping("/user/statistic/credit")
	public String show(HttpSession session){
		return "myPublicCourse";
	}
}
