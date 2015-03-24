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
import org.springframework.web.bind.annotation.PathVariable;
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
public class CourseDataContorller {
	@Autowired
	private ICourseService courseService ;
	@Autowired
	private ITermService termService;
	@Autowired
	private IAccountService accountService;
	@Autowired
	private IOrganizationService organizationService;
	@RequestMapping(value={"","/courses"})
	public String show(HttpServletRequest request){
		List<Term> terms = termService.getTerms();
		List<Course> list = courseService.getCourses(1,PageSize.ADMIN_COURSE.getSize(),true);
		request.setAttribute("terms", terms);
		request.setAttribute("courses", list);
		return "admin_course";
	}
	@RequestMapping(value="/courses/page/{pageNum}")
	public String showPage(@PathVariable Integer pageNum,HttpServletRequest request){
		List<Term> terms = termService.getTerms();
		List<Course> list = courseService.getCourses(pageNum,PageSize.ADMIN_COURSE.getSize(),true);
		request.setAttribute("terms", terms);
		request.setAttribute("courses", list);
		return "admin_course";
	}
	@ResponseBody
	@RequestMapping(value="/task/progress")
	public Map<String,Object> getProgress(HttpSession session){
		Object ob = session.getAttribute(SessionKey.PROGRESS.toString());
		if(ob==null){
			return WebUtils.webJsonResult(0);
		}
		double result = (Double) ob;
		return WebUtils.webJsonResult(result);
	}
	@ResponseBody
	@RequestMapping(value="/courses",method=RequestMethod.POST)
	public Map<String,Object> saveDb(HttpSession session){
		//从session 获取到 数据
		Object courseFullOb = session.getAttribute(SessionKey.FETCH_COURSE_FULL_DATA_LIST.toString());
		if(courseFullOb==null){
			return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
		}
		//保存到数据库
		List<CoursePageBo> coursePageBos = (List<CoursePageBo>) courseFullOb;
		List<Course> courses = CoursePageBo2Course(coursePageBos);
		int addCount=0;
		int existCount=0;
		for(Course course:courses){
			try {
				courseService.addCourse(course);
				addCount++;
			} catch (ObjectExistsException e) {
				existCount++;
			}
		}
		session.removeAttribute(SessionKey.FETCH_COURSE_FULL_DATA_LIST.toString());
		Map<String,Integer> result = new HashMap<String, Integer>();
		result.put("addCount", addCount);
		result.put("existCount", existCount);
		return WebUtils.webJsonResult(result);
	}
	@ResponseBody
	@RequestMapping(value="/courses",method=RequestMethod.PUT)
	public Map<String,Object> updateSyc(HttpSession session ,Integer step ,String termNos,String vCode){
		FetchHelper fetchHelper = new FetchHelper();
		if(step!=null){ // 进度由服务器回送 step 1 : 10% step 2：10% step 3 :10% step: 70%
			
			if(step==1){
				
				//检测是不是有输入验证码
				if(vCode==null&&vCode.trim().equals("")){
					return WebUtils.webJsonError(Error.VALIDATECODE_NULL_ERROR);  
				}
				//获取到验证码
				 Object cookiesOb = session.getAttribute(SessionKey.VCODE_COOKIE.toString());
				 if(cookiesOb==null){
					 return WebUtils.webJsonError(Error.GET_VALIDATECODE_ERROR); 
				 }
				 String cookies = (String) cookiesOb;
				 //用内置账户登录，获得权限
				 List<Account> accounts = accountService.getAccountsByCampusId(Campus.HAIZHU.getId());
				 if(accounts==null||accounts.size()==0){
					 return WebUtils.webJsonError(Error.FETCH_ACCOUNT_NULL); 
				 }
				 Account account = accounts.get(0);
				 IWebLogin webLogin = new WebLogin(cookies);
				try {
					 cookies =webLogin.login(account.getUsername(), account.getPassword(), vCode);
				} catch (LoginOutTimeException e) {
					session.removeAttribute(SessionKey.VCODE_COOKIE.toString());
					 return WebUtils.webJsonError(Error.LOGIN_OUT_TIME); 
				} catch (ConnectionException e) {
					session.removeAttribute(SessionKey.VCODE_COOKIE.toString());
					 return WebUtils.webJsonError(Error.CONNECTION_ERROR); 
				} catch (SnoOrPasswordIncorrectException e) {
					session.removeAttribute(SessionKey.VCODE_COOKIE.toString());
					 return WebUtils.webJsonError(Error.FETCH_ACCOUNT_USERNAME_PASSWORD_ERROR); 
				} catch (ValidateCodeIncorrectException e) {
					session.removeAttribute(SessionKey.VCODE_COOKIE.toString());
					 return WebUtils.webJsonError(Error.VALIDATECODE_ERROR); 
				}
				//登录成功后
				Map<String, Object> result = new HashMap<String,Object>();
				result.put("result", "success");
				session.setAttribute(SessionKey.PROGRESS.toString(), 10.0);
				return WebUtils.webJsonResult(result);
			}else if(step==2){
				// 获取到 基础的 课程 信息 name no nameNo
				List<Course> courseBasicList =null;
				JSONArray JsonArray = (JSONArray) JSONArray.parse(termNos);
				for(Object jsontermNo : JsonArray){
					String termNo= 	(String) jsontermNo;
					try {
						courseBasicList = fetchHelper.fetchCourseBasicInfo(termNo);
						session.setAttribute(SessionKey.FETCH_COURSE_BASICAL.toString()+termNo, courseBasicList);
					} catch (FetchTimeoutException e) {
						return WebUtils.webJsonError(Error.FETCH_TIME_OUT);
					}
				}
				session.setAttribute(SessionKey.PROGRESS.toString(), 20.0);
				return WebUtils.webJsonResult("done");
			}else if(step==3){
				//从session中获取 课程号 
				List<Term> terms = termService.getTerms();
				Set<String> courseNos = new HashSet<String>();
				for(Term term:terms){
					Object ob = session.getAttribute(SessionKey.FETCH_COURSE_BASICAL.toString()+term.getNo());
					if(ob!=null){
						List<Course> list = (List<Course>) ob;
						for(Course course:list){
							courseNos.add(course.getNo());
						}
					}
					session.removeAttribute(SessionKey.FETCH_COURSE_BASICAL.toString()+term.getNo());
				}
				session.setAttribute(SessionKey.FETCH_COURSE_BASICAL_DATA_SET.toString(), courseNos);
				Map<String, Integer> result = new HashMap<String ,Integer>();
				result.put("all", courseNos.size());
				session.setAttribute(SessionKey.PROGRESS.toString(), 30.0);
				return WebUtils.webJsonResult(result);
			}else if(step==4){
				double currentProgress = 30.0;
				//获取到验证码
				 Object cookiesOb = session.getAttribute(SessionKey.VCODE_COOKIE.toString());
				 if(cookiesOb==null){
					 return WebUtils.webJsonError(Error.FETCH_NO_ACCESS_ERROR);  //code = -50007
				 }
				String cookies =(String) cookiesOb;
				Object ob = session.getAttribute(SessionKey.FETCH_COURSE_BASICAL_DATA_SET.toString());
				if(ob!=null){
					Set<String> courseNos= (Set<String>) ob;
					double perStep=60.0/courseNos.size();
					List<CoursePageBo> coursePages = new ArrayList<CoursePageBo>();
					List<String> remainCourses = new ArrayList<String>();
					for(String no:courseNos){
						currentProgress+=perStep;
						try {
							CoursePageBo list = fetchHelper.fetchCourseFullInfo(no, cookies);
							coursePages.add(list);
						} catch (FetchTimeoutException e) {
							remainCourses.add(no);
						}
						session.setAttribute(SessionKey.PROGRESS.toString(),currentProgress);
					}
					session.removeAttribute(SessionKey.FETCH_COURSE_BASICAL_DATA_SET.toString());
					session.setAttribute(SessionKey.FETCH_COURSE_FULL_DATA_LIST.toString(), coursePages);
					session.setAttribute(SessionKey.PROGRESS.toString(),80.0);
					Map<String,Integer> result = new HashMap<String, Integer>();
					result.put("done", coursePages.size());
					result.put("remain", remainCourses.size());
					session.setAttribute("", "");
					return WebUtils.webJsonResult(result );
				}else{
					return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
				}
			}
		}else{
			return WebUtils.webJsonError(Error.PARAMETER_ERROR);
		}
		return WebUtils.webJsonResult("done");
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
	
	
	public List<Course> CoursePageBo2Course(List<CoursePageBo> coursePageBos){
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
			course.setQualityHours(Double.valueOf(coursePageBo.getQualityHours()));
			course.setTeachingHours(Double.valueOf(coursePageBo.getTeachingHours()));
			course.setExperimentalHours(Double.valueOf(coursePageBo.getExperimentalHours()));
			course.setComputerClassHours(Double.valueOf(coursePageBo.getComputerClassHours()));
			course.setOtherClassHours(Double.valueOf(coursePageBo.getOtherClassHours()));
			course.setTeachingProgram(coursePageBo.getTeachingProgram());
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
