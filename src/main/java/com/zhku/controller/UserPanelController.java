package com.zhku.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Pagination;
import com.zhku.bean.PublicCourseComment;
import com.zhku.bean.Student;
import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.module.fetchData.bo.StudentPage;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.IPublicCourseCommentService;
import com.zhku.service.db.IStudentService;
import com.zhku.service.db.ITermService;
import com.zhku.service.db.IUserService;
import com.zhku.service.network.impl.WebLogin;
import com.zhku.utils.RegExpValidator;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("main/user")
public class UserPanelController {
	@Autowired
	private ITermService termService;
	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private IStudentService studentService;
	@Autowired
	private IUserService userService ;
	@Autowired
	private IPublicCourseCommentService publicCourseCommentService;
	@RequestMapping("/comment/me")
	public String showMyComment(HttpSession session,HttpServletRequest request){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			session.setAttribute("message", "你已经退出登录，请登录后重试" );
			return "error";
		}
		String currentPage =request.getParameter("currentPage");
		Pagination<PublicCourseComment> pagination = new Pagination<PublicCourseComment>();
		pagination.setPageSize(Constants.PageSize.MY_COMMENT.getSize());
		if(currentPage==null || !RegExpValidator.IsIntNumber(currentPage)){
			pagination.setCurrentPage(1);
		}else{
			pagination.setCurrentPage(Integer.valueOf(currentPage));
		}
		List<PublicCourseComment> comments = publicCourseCommentService.getPublicCourseCommentsByUid(user.getUid());
		pagination.setPageDataList(comments);
		request.setAttribute("pagination", pagination);
		return "mycomment";
	}
	@RequestMapping("/comment/receive")
	public String showCommentsReplyMe(HttpSession session,HttpServletRequest request){
		User user = SecurityUtil.getUser(session);
		if(user==null){
			session.setAttribute("message", "你已经退出登录，请登录后重试" );
			return "error";
		}
		String currentPage =request.getParameter("currentPage");
		Pagination<PublicCourseComment> pagination = new Pagination<PublicCourseComment>();
		pagination.setPageSize(Constants.PageSize.MY_COMMENT.getSize());
		if(currentPage==null || !RegExpValidator.IsIntNumber(currentPage)){
			pagination.setCurrentPage(1);
		}else{
			pagination.setCurrentPage(Integer.valueOf(currentPage));
		}
		List<PublicCourseComment> comments = publicCourseCommentService.getPublicCourseCommentReplyByUid(user.getUid());
		pagination.setPageDataList(comments);
		request.setAttribute("pagination", pagination);
		for(PublicCourseComment comment :comments){
			if(comment.getState()==0){
				comment.setState(1);
				publicCourseCommentService.updatePublicCourseComment(comment);
			}
		}
		return "commentReplyMe";
	}
	
	@RequestMapping(value={"","/passport"})
	public String showPassport(HttpSession session,HttpServletRequest request){
		if(!SecurityUtil.checkLogin(session)){
			return "redirect:/index";
		}
		List<Term> terms =termService.getAvailabelTerms();
		request.setAttribute("terms", terms);
		return "passport_basic";
	}
	@ResponseBody
	@RequestMapping(value="/bind/local",method=RequestMethod.POST)
	public Map<String, Object> bindByUser(String studentName,String sNo,String classNo,String sex,HttpSession session){
//		new Student();
		if(!SecurityUtil.checkLogin(session)){
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		Student student  =studentService.getStudentBySno(sNo);
		if(student==null){
			student=new Student();
			student.setName(studentName);
			student.setSex(sex);
			student.setsNo(sNo);
			BaseClass classBelongTo  = new BaseClass();
			classBelongTo.setNo(classNo);
			student.setClassBelongTo(classBelongTo);
			studentService.addStudent(student);
//			return WebUtils.webJsonError("你已经绑定过了不需要绑定!");
		}
		User user = SecurityUtil.getUser(session);
		user.setStudent(student);
		userService.updateUser(user);
		user = userService.getUserById(user.getUid());
		session.setAttribute(SessionKey.LOGIN_USER.toString(), user);
		return WebUtils.webJsonResult("success");
	}
	@ResponseBody
	@RequestMapping(value="/bind",method=RequestMethod.POST)
	public Map<String,Object> bind(String sno ,String password,String vCode,HttpSession session){
		if(!SecurityUtil.checkLogin(session)){
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		if(sno==null||sno.trim().equals("")){
			return WebUtils.webJsonError(Error.SNO_NULL_ERROR);
		}
		if(password==null||password.trim().equals("")){
			return WebUtils.webJsonError(Error.PASSWORD_NULL_ERROR);
		}
		if(vCode==null||vCode.trim().equals("")){
			return WebUtils.webJsonError(Error.VALIDATECODE_NULL_ERROR);
		}
		String vCodeCookie = (String) session.getAttribute(SessionKey.VCODE_COOKIE.toString());
		WebLogin webLogin  = new WebLogin();
		webLogin.setCookies(vCodeCookie);
		try {
			webLogin.login(null,sno, password, vCode);
		} catch (LoginOutTimeException e) {
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		} catch (ConnectionException e) { 
			return WebUtils.webJsonError(Error.CONNECTION_ERROR);
		} catch (SnoOrPasswordIncorrectException e) {
			return WebUtils.webJsonError(Error.USERNAME_OR_PASSWORD_ERROR);
		} catch (ValidateCodeIncorrectException e) {
			return WebUtils.webJsonError(Error.VALIDATECODE_ERROR);
		}
		FetchHelper fetchHelper = new FetchHelper();
		try {
			Student student = studentService.getStudentBySno(sno);
			if(student==null){
				StudentPage studentPage = fetchHelper.fetchStudentPage(vCodeCookie, sno);
				BaseClass clazz = baseClassService.getBaseClassByClassName(studentPage.getClassName().trim());
				if(clazz==null){
					return WebUtils.webJsonError(Error.BIND_ACCOUT_FAIL);
				}
				student = new Student();
				student.setClassBelongTo(clazz);
				student.setName(studentPage.getsName());
				student.setSex(studentPage.getsSex());
				student.setsNo(studentPage.getSno());
				studentService.addStudent(student);
			}
			User user = (User) session.getAttribute(SessionKey.LOGIN_USER.toString());
			user.setStudent(student);
			
			session.setAttribute(SessionKey.LOGIN_USER.toString(), user);
			userService.updateUser(user);
			return WebUtils.webJsonResult("success");
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Error.BIND_ACCOUT_FAIL);
		}
	}
	@RequestMapping(value={"/avater"})
	public String userAvator(HttpSession session,HttpServletRequest request){
		if(!SecurityUtil.checkLogin(session)){
			return "redirect:index";
		}
		return "passport_avater";
	}
	@ResponseBody
	@RequestMapping(value={""},method=RequestMethod.POST)
	public Map<String,Object> saveUser(String nickName,String description,HttpSession session,HttpServletRequest request){
		User user = (User) session.getAttribute(SessionKey.LOGIN_USER.toString());
		user.setDescription(description);
		user.setNickName(nickName);
		userService.updateUser(user);
		return WebUtils.webJsonResult("success");
	}

	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}

	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}

	public IStudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(IStudentService studentService) {
		this.studentService = studentService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public IPublicCourseCommentService getPublicCourseCommentService() {
		return publicCourseCommentService;
	}

	public void setPublicCourseCommentService(IPublicCourseCommentService publicCourseCommentService) {
		this.publicCourseCommentService = publicCourseCommentService;
	}
}
