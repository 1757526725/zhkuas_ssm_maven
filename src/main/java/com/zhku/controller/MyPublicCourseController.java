package com.zhku.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IMyPublicCourseService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/main/user")
public class MyPublicCourseController {
	@Autowired
	private ITermService termService;
	@Autowired
	private IMyPublicCourseService myPublicCourseService;
	@RequestMapping("/course/statistics")
	public String show(){
		return "myPublicCourse";
	}
	//更新统计信息
	@ResponseBody
	@RequestMapping(value="/course/statistics",method = RequestMethod.PUT)
	public Map<String,Object> updateStatistics(HttpSession session){
		//检查是否登录系统 (本系统)
		User user = SecurityUtil.getUser(session);
		if(user == null) return WebUtils.webJsonError(Error.PERMISSIONS_DO_NOT_ALLOW);
		//判断是不是已经绑定学号
		if(user.getStudent()==null) return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_UNBIND);
		//获取教务网登录session （cookie）
		String cookie= (String) session.getAttribute(SessionKey.STUDENT_COOKIE.toString()+user.getStudent().getsNo());
		if(cookie==null) return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		
		FetchHelper fetchHelper = new FetchHelper();
		List<Term> terms =termService.getStudentTerms(user.getStudent().getsNo());
		List<MyPublicCourse> list = null;
		//调用FetchHelper抓取最新的数据
		try {
			list = fetchHelper.fetchMyPublicCourses(cookie, user.getUid(), terms);
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Error.FAILURE);
		}
		if(list == null||list.size()==0){
			return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
		}
		//写入数据库
		for (MyPublicCourse myPublicCourse : list) {
			MyPublicCourse publicCourse = myPublicCourseService.getMyPublicCouseByCNameNoAndUid(myPublicCourse.getcNameNo(),myPublicCourse.getUid());
			if (publicCourse == null) {
				myPublicCourseService.addMyPublicCouse(myPublicCourse);
			} else {
				publicCourse.setcNameNo(myPublicCourse.getcNameNo());
				publicCourse.setScore(myPublicCourse.getScore());
				myPublicCourseService.updateMyPublicCouse(publicCourse);
			}
		}
		return WebUtils.webJsonResult("更新成功!");
	}

	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
	public IMyPublicCourseService getMyPublicCourseService() {
		return myPublicCourseService;
	}
	public void setMyPublicCourseService(
			IMyPublicCourseService myPublicCourseService) {
		this.myPublicCourseService = myPublicCourseService;
	}
}
