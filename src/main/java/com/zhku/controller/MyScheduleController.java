package com.zhku.controller;

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
import com.zhku.bean.MySchedule;
import com.zhku.bean.Student;
import com.zhku.bean.Term;
import com.zhku.bean.TermCourse;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.IMyScheduleService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/main")
public class MyScheduleController {

	@Autowired
	private IMyScheduleService myScheduleService;
	@Autowired
	private ITermService termService;
	@Autowired
	private IBaseClassService baseClassService;
	@RequestMapping("/user/curriculum/schedule")
	public String show(HttpSession session ,HttpServletRequest request){
		if(!SecurityUtil.checkLogin(session)){
			return "redirect:/index";
		}
		User user = SecurityUtil.getUser(session);
		List<Term> terms = null;
		Term currentTerm = null;
		Object obj = session.getAttribute("terms");
		if(obj==null){
			terms =termService.getAvailabelTerms();
		}else{
			terms = (List<Term>)obj;
		}
		for(Term term:terms){
			if(term.isCurrent()){
				currentTerm = term;
				request.setAttribute("term", currentTerm);
				break;
			}
		}
		if(currentTerm!=null){
			List<MySchedule> schedules = myScheduleService.getMySchedulesByTermNoAndUid(currentTerm.getNo(), user.getUid());
			if(schedules == null ||schedules.size()==0){
					Student student = user.getStudent();
					if(student!=null){
						BaseClass clazz = student.getClassBelongTo();
						if(clazz!=null){
							clazz = baseClassService.getBaseClassWithCourseListByClassNoAndTermNo(clazz.getNo(), currentTerm.getNo());
							request.setAttribute("clazz", clazz);
						}
					}
			}
			request.setAttribute("schedules", schedules);
			
		}
		return "mySchedule";
	}
	@ResponseBody
	@RequestMapping(value = "/user/curriculum/schedule",method = RequestMethod.POST)
	public Map<String,Object> save(HttpSession session,HttpServletRequest request){
		String[] cNos = request.getParameterValues("cNo");
		Object obj = session.getAttribute(SessionKey.LOGIN_USER.toString());
		if(obj==null){
			return WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		User user= (User)(obj);
		Student  student  = user.getStudent();
		if(student==null||student.getsNo()==null||student.getsNo().trim().equals("")){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_UNBIND);
		}
		Term term  = termService.getCurrentTerm();
		List<MySchedule> schedules =  myScheduleService.getMySchedulesByTermNoAndUid(term.getNo(), user.getUid());
		
		if (schedules == null||schedules.size()==0) {
			BaseClass clazz = baseClassService.getBaseClassWithCourseListByClassNoAndTermNo(student.getClassBelongTo().getNo(), term.getNo());
			if (clazz != null && clazz.getCourseList() != null) {
				for (TermCourse termCourse : clazz.getCourseList()) {
					// 插入一条schedule
					MySchedule mySchedule = new MySchedule();
					mySchedule.setTermCourse(termCourse);
					mySchedule.setTermCourseId(termCourse.getId());
					mySchedule.setTermNo(term.getNo());
					mySchedule.setUid(user.getUid());
					if (checkIsSelected(termCourse, cNos)) {
						mySchedule.setState(1);
					} else {
						mySchedule.setState(0);
					}
					myScheduleService.addMySchedule(mySchedule);
				}
			}
		} else {
			for (MySchedule mySchedule : schedules) {
				if (checkIsSelected(mySchedule.getTermCourse(), cNos)) {
					mySchedule.setState(1);
				} else {
					mySchedule.setState(0);
				}
				myScheduleService.updateMySchedule(mySchedule);
			}
		}
		return WebUtils.webJsonResult("success");
	}
	private boolean checkIsSelected(TermCourse termCourse, String[] cNos) {
		for (String cNo : cNos) {
			if (cNo.equals(termCourse.getCourse().getNo()))
				return true;
		}
		return false;
	}
	public IMyScheduleService getMyScheduleService() {
		return myScheduleService;
	}
	public void setMyScheduleService(IMyScheduleService myScheduleService) {
		this.myScheduleService = myScheduleService;
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}
	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
}
