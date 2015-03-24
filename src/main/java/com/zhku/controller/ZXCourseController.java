package com.zhku.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.BasicNameValuePair;
import zhku.jackcan.webCrawler.NameValuePair;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.CourseListPage;
import com.zhku.bean.Student;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.ICourseListPageService;
import com.zhku.service.db.IStudentService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/main")
public class ZXCourseController {
	
	@Autowired
	private ICourseListPageService courseListPageService;
	private IStudentService studentService;
	@RequestMapping("/user/course/public/zx")
	public String show(HttpSession session ,HttpServletRequest request){
		return "zxCourse";
	}
	@ResponseBody
	@RequestMapping(value = "/user/course/public/zx",method =RequestMethod.POST)
	public Map<String,Object> submit(HttpSession session ,HttpServletRequest request){
		if(!SecurityUtil.checkLogin(session)){
			return  WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		User user = SecurityUtil.getUser(session);
		String sNo = (String) session.getAttribute(SessionKey.STUDENT_NO.toString());
		if(sNo==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		String cookie = (String) session.getAttribute(SessionKey.STUDENT_COOKIE.toString()+sNo);
		if(cookie==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		
		Enumeration paramNames=request.getParameterNames();
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); //创建参数队列
		while(paramNames.hasMoreElements()){
			String name = (String) paramNames.nextElement();
			String value =request.getParameter(name);
			formParams.add(new BasicNameValuePair(name,value));
		}
		String result=null;
		try {
			result = new FetchHelper().submitCourse(cookie, formParams);
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Error.SUBMIT_TIME_OUT);
		}
		if(result ==null){
			return WebUtils.webJsonError(Error.SUBMIT_TIME_OUT);
		}
		return WebUtils.webJsonResult(result);
	}
	@ResponseBody
	@RequestMapping("/user/course/public/zx/list")
	public Map<String,Object> getZxCourseList(HttpSession session ,HttpServletResponse response,HttpServletRequest request) throws IOException{
		if(!SecurityUtil.checkLogin(session)){
			return  WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		User user = SecurityUtil.getUser(session);
		Student student = null;
		String sNo = (String) session.getAttribute(SessionKey.STUDENT_NO.toString());
		if(sNo==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		String sel_lx = request.getParameter("sel_lx");
		String selSpeciality = request.getParameter("SelSpeciality");
		String campusId = request.getParameter("campus");
		String cookie = (String) session.getAttribute(SessionKey.STUDENT_COOKIE.toString()+sNo);
		if(cookie==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		CourseListPage courseListPage = null;
		if (sel_lx != null) {
			try {
				if (sel_lx.equals("0")) {
//					courseListPage = httpService.getCourseListPage(
//							selSpeciality, cookie);
					return WebUtils.webJsonError(Error.IS_NOT_TIME);
				} else if (sel_lx.equals("2")) {
					String key="courseList_"+campusId;
//					courseListPage=(CourseListPage) CacheUtils.get(key);
					if(courseListPage==null){
						courseListPage = courseListPageService.getCourseListPageByMajorNo(campusId);
//						CacheUtils.add(key, courseListPage);
					}
					if (courseListPage == null) {
						courseListPage = new FetchHelper().fetchZXCourseListPage(cookie, campusId);
						if(courseListPage!=null)
							courseListPageService.addCourseListPage(courseListPage);
					}
				}
			} catch (FetchTimeoutException e) {
				return WebUtils.webJsonError(Error.FETCH_TIME_OUT);
			}
			if(courseListPage==null){
				return WebUtils.webJsonError(Error.FETCH_NO_DATA);
			}
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(courseListPage.getPageContent());
			return null;
		}
		return null;
	}

	public ICourseListPageService getCourseListPageService() {
		return courseListPageService;
	}

	public void setCourseListPageService(ICourseListPageService courseListPageService) {
		this.courseListPageService = courseListPageService;
	}

	public IStudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}
}
