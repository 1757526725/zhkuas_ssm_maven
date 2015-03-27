package com.zhku.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.BaseClass;
import com.zhku.bean.MyCourseScheme;
import com.zhku.bean.MySchedule;
import com.zhku.bean.Student;
import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.IMyCourseSchemeService;
import com.zhku.service.db.IMyScheduleService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.ServiceUtils;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/main")
public class MySchemeController {

	@Autowired
	private IMyCourseSchemeService myCourseSchemeService;
	@Autowired
	private ITermService termService;
	@Autowired 
	private IMyScheduleService myScheduleService;
	@Autowired
	private IBaseClassService baseClassService;
	@RequestMapping("/user/curriculum/scheme")
	public String show(HttpSession session, HttpServletRequest request) {
		if (!SecurityUtil.checkLogin(session)) {
			return "redirect:/index";
		}
		List<Term> terms = null;
		Term currentTerm = null;
		Object obj = session.getAttribute("terms");
		if (obj == null) {
			terms = termService.getAvailabelTerms();
		} else {
			terms = (List<Term>) obj;
		}
		for (Term term : terms) {
			if (term.isCurrent()) {
				currentTerm = term;
				request.setAttribute("term", currentTerm);
				break;
			}
		}

		User user = (User) session.getAttribute(SessionKey.LOGIN_USER.toString());
		List<MyCourseScheme> schemes = myCourseSchemeService.getMyCourseSchemesByTermNoAndUid(currentTerm.getNo(), user.getUid());
		Student student = user.getStudent();
		if (student != null) {
			String loginCookie = (String) session.getAttribute(Constants.SessionKey.STUDENT_COOKIE + student.getsNo());
			if (loginCookie != null) {
				request.setAttribute("acadmey_had_logined", true);
			}
		}
		session.setAttribute(Constants.SessionKey.MYSCHEMES.toString(), schemes);
		request.setAttribute("nav_actived", "my_project");
		request.setAttribute("schemes", schemes);
		return "myScheme";
	}

	@ResponseBody
	@RequestMapping(value = "/user/curriculum/scheme", method = RequestMethod.POST)
	public Map<String, Object> addScheme(HttpSession session, HttpServletRequest request) {
		if (!SecurityUtil.checkLogin(session)) {
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		String cNo = request.getParameter("cNo");
		Term term = null;
		String displayValue = request.getParameter("displayValue");
		String formValue = request.getParameter("formValue");
		String schoolTime = request.getParameter("schoolTime");
		Object obj = session.getAttribute("term");
		if (obj != null) {
			term = (Term) obj;
		} else {
			term = termService.getCurrentTerm();
		}
		User user = (User) session.getAttribute(SessionKey.LOGIN_USER.toString());
		int uid = user.getUid();
		MyCourseScheme myCourseScheme = new MyCourseScheme();
		if (cNo != null && displayValue != null && formValue != null && uid != 0) {
			myCourseScheme.setcNo(cNo);
			myCourseScheme.setDisplayValue(displayValue);
			myCourseScheme.setFormValue(formValue);
			myCourseScheme.setTerm(term);
			myCourseScheme.setUid(uid);
			myCourseScheme.setSchoolTime(schoolTime);
			MyCourseScheme findScheme = myCourseSchemeService.findMyCourseScheme(myCourseScheme);
			if (findScheme != null) {
				myCourseScheme.setId(findScheme.getId());
				myCourseScheme.setOrderNum(findScheme.getOrderNum());
				myCourseSchemeService.updateMyCourseScheme(myCourseScheme);
				return WebUtils.webJsonResult("update success");
			}
			myCourseSchemeService.addMyCourseScheme(myCourseScheme);
			List schemes = (List) session.getAttribute(Constants.SessionKey.MYSCHEMES.toString());
			if (schemes != null) {
				schemes.add(myCourseScheme);
				session.setAttribute(Constants.SessionKey.MYSCHEMES.toString(), schemes);
			}
			return WebUtils.webJsonResult("add success");
		}else{
			return WebUtils.webJsonError(Error.FAILURE);
		}

	}
	@ResponseBody
	@RequestMapping(value = "/user/curriculum/scheme/{id}", method = RequestMethod.DELETE)
	public Map<String,Object> deleteScheme(@PathVariable Integer id ,HttpSession session,HttpServletRequest request){
		if (!SecurityUtil.checkLogin(session)) {
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		if(id!=null){
			MyCourseScheme myCourseScheme =new MyCourseScheme();
			myCourseScheme.setId(id);
			myCourseSchemeService.deleteMyCourseScheme(myCourseScheme);
			session.setAttribute("scaning", true);
			return WebUtils.webJsonResult("success");
		}
		return WebUtils.webJsonError(Error.FAILURE);
	}
	@ResponseBody
	@RequestMapping(value = "/user/curriculum/scheme", method = RequestMethod.PUT)
	public Map<String ,Object> saveSchemeOreder(HttpSession session, HttpServletRequest request){
		if (!SecurityUtil.checkLogin(session)) {
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		String[] msids =request.getParameterValues("msid");
		for(int i=0 ;i<msids.length;++i){
			MyCourseScheme myCourseScheme = new MyCourseScheme();
			myCourseScheme.setId( Integer.valueOf(msids[i]));
			myCourseSchemeService.updateOrdeNumOfMyCourseScheme(myCourseScheme , i+1);
		}
		return WebUtils.webJsonResult("success");
	}
	@ResponseBody
	@RequestMapping(value = "/user/curriculum/scheme/check", method = RequestMethod.GET)
	public Map<String ,Object> checkScheme(HttpSession session, HttpServletRequest request){
		if (!SecurityUtil.checkLogin(session)) {
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		User user = SecurityUtil.getUser(session);
		Term term = (Term) session.getAttribute("term");
		if(term==null){
			term = termService.getCurrentTerm();
			session.setAttribute("term", term);
		}
		Map<String, String> params = new HashMap<String, String>();
//		params.put("termNo", Constants.CURRENT_TERMNO);
//		params.put("uid", String.valueOf(user.getUid()));
		List<MySchedule> schedules = myScheduleService.getMySchedulesByTermNoAndUid(term.getNo(), user.getUid());
		if(schedules==null||schedules.size()==0){
			//获取班级课表 
			 Student student = user.getStudent();
			 if(student  == null){
				return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_UNBIND);
			 }else{
				BaseClass clazz = baseClassService.getBaseClassWithCourseListByClassNoAndTermNo(student.getClassBelongTo().getNo(), term.getNo());
				if(clazz.getCourseList()!=null&&clazz.getCourseList().size()>0){
					return WebUtils.webJsonError(Error.HAVE_NOT_ENSURE_SCHEDULE);
				}else{
					schedules = new ArrayList<MySchedule>();
				}
			 }
		}
		List<MyCourseScheme> schemes=(List<MyCourseScheme>) request.getSession().getAttribute(SessionKey.MYSCHEMES.toString());
	//	schemes=schemeService.getMyCourseSchemesByTermNoAndUid(params);
		Map<String,String> result=null;
		if(schemes!=null&&schemes.size()>0){
			result=ServiceUtils.conflictDetectionCourse(schedules, schemes);
		}
		if(result==null||result.size()==0){
			return WebUtils.webJsonResult("OK");
		}
		return WebUtils.webJsonResult(result);
	}
	
	public IMyCourseSchemeService getMyCourseSchemeService() {
		return myCourseSchemeService;
	}

	public void setMyCourseSchemeService(IMyCourseSchemeService myCourseSchemeService) {
		this.myCourseSchemeService = myCourseSchemeService;
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public IMyScheduleService getMyScheduleService() {
		return myScheduleService;
	}

	public void setMyScheduleService(IMyScheduleService myScheduleService) {
		this.myScheduleService = myScheduleService;
	}

	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}

	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
}
