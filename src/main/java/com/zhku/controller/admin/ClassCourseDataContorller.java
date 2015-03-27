package com.zhku.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhku.bean.Academy;
import com.zhku.bean.Account;
import com.zhku.bean.Course;
import com.zhku.bean.Organization;
import com.zhku.bean.Term;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.ObjectExistsException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.module.fetchData.bo.CoursePageBo;
import com.zhku.service.db.IAccountService;
import com.zhku.service.db.ICourseService;
import com.zhku.service.db.IOrganizationService;
import com.zhku.service.db.ITermService;
import com.zhku.service.network.IWebLogin;
import com.zhku.service.network.impl.WebLogin;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Campus;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.PageSize;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("admin/course")
public class ClassCourseDataContorller {
	@Autowired
	private ICourseService courseService ;
	@Autowired
	private ITermService termService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOrganizationService organizationService;
	@RequestMapping(value="/class/courses")
	public String show(HttpServletRequest request){
		List<Term> terms = termService.getTerms();
		request.setAttribute("terms", terms);
		return "admin_classCourse";
	}
	@ResponseBody
	@RequestMapping(value="/class/courses/task/progress")
	public Map<String,Object> getProgress(HttpSession session){
		Object ob = session.getAttribute(SessionKey.PROGRESS.toString());
		if(ob==null){
			return WebUtils.webJsonResult(0);
		}
		double result = (Double) ob;
		return WebUtils.webJsonResult(result);
	}
	@ResponseBody
	@RequestMapping(value="/class/courses",method=RequestMethod.POST)
	public Map<String,Object> saveDb(HttpSession session){
	
		return null;
	}
	@ResponseBody
	@RequestMapping(value="/class/courses",method=RequestMethod.PUT)
	public Map<String,Object> updateSyc(HttpSession session ,Integer step ,String termNos,String vCode){
	
		
		return null;
	}
	
	public ICourseService getCourseService() {
		return courseService;
	}
	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}
	public IAccountService getAccountService() {
		return accountService;
	}
	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}
	
	
	public List<Course> ClassCoursePageBo2ClassCourse(List<CoursePageBo> coursePageBos){
		List<Course> courses = new ArrayList<Course>();
		Course course =null;
		Organization organization = null;
		for(CoursePageBo coursePageBo :coursePageBos){
			course = new Course();
			course.setNo(coursePageBo.getNo());
			course.setCredits(Double.valueOf(coursePageBo.getCredits()));
			course.setEnglishName(coursePageBo.getEnglishName());
			course.setNameNo(coursePageBo.getNameNo());
			course.setName(coursePageBo.getName());
			course.setCourseType(coursePageBo.getCourseType());
			course.setQualityHours(Double.valueOf(course.getQualityHours()));
			course.setTeachingHours(Double.valueOf(coursePageBo.getTeachingHours()));
			course.setExperimentalHours(Double.valueOf(coursePageBo.getExperimentalHours()));
			course.setComputerClassHours(Double.valueOf(coursePageBo.getComputerClassHours()));
			course.setOtherClassHours(Double.valueOf(coursePageBo.getOtherClassHours()));
			organization = organizationService.getOrganizationByName(coursePageBo.getOrganization().trim());
			course.setOrganization(organization);
			courses.add(course);
		}
		return courses;
	}
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
}
