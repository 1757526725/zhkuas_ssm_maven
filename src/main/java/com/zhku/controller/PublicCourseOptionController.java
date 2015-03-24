package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.CampusArea;
import com.zhku.bean.MyCourseScheme;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.IMyCourseSchemeService;
import com.zhku.service.db.IPublicCourseOptionService;
import com.zhku.service.db.ITermService;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/main")
public class PublicCourseOptionController {
	@Autowired
	private IPublicCourseOptionService publicCourseOptionService;
	@Autowired
	private ITermService termService;
	@Autowired
	private ICampusAreaService campusAreaService ;
	@Autowired
	private IMyCourseSchemeService courseSchemeService;
	@RequestMapping("/course/public/option/{cNo}/campus/{campusId}/term/{termNo}")
	public String getOptionHtml(@PathVariable String cNo,@PathVariable Integer campusId,@PathVariable String termNo,HttpServletRequest request,HttpSession session){
		Term currentTem = null;
		CampusArea campus = null;
		User user = null;
		Object obj = session.getAttribute(SessionKey.LOGIN_USER.toString());
		if(obj==null){
			request.setAttribute("message", "没有权限，请登录后再做操作!");
			return "innerHtmlError";
		}else{
			user = (User) obj;
		}
		if(termNo!=null){
			currentTem = termService.getTermByNo(termNo);
		}else{
			List<Term> terms = (List<Term>) session.getAttribute("terms");
			for(Term term:terms){
				if(term.isCurrent()){
					currentTem = term;
					break;
				}
			}
		}
		if(campusId!=null){
			campus  = campusAreaService.getCampusAreaById(campusId);
		}
		if(currentTem == null ||campus ==null ){
			request.setAttribute("message", "参数错误，请重试!");
			return "innerHtmlError";
		}
		PublicCourseOption publicCourseOption = new PublicCourseOption();
		publicCourseOption.setTermNo(currentTem.getNo());
		publicCourseOption.setcNo(cNo);
		publicCourseOption.setCampus(campus);
		publicCourseOption = publicCourseOptionService.findPublicCourseOption(publicCourseOption );
		MyCourseScheme myCourseScheme = new MyCourseScheme();
		myCourseScheme.setcNo(cNo);
		myCourseScheme.setTerm(currentTem);
		myCourseScheme.setUid(user.getUid());
		myCourseScheme = courseSchemeService.findMyCourseScheme(myCourseScheme);
		request.setAttribute("myCourseScheme", myCourseScheme);
		request.setAttribute("option", publicCourseOption);
		return "courseOption";
	}
	@RequestMapping(value = "/course/public/option",method =RequestMethod.GET)
	public String getSynOption(HttpServletRequest request,HttpSession session){
		String value = request.getParameter("value");
		String campusId = request.getParameter("campus");
		String skbjval = request.getParameter("skbjval");
		String sNo =(String) session.getAttribute(SessionKey.STUDENT_NO.toString());
		if(sNo==null){
			request.setAttribute("message", "未登录教务网!");
			return "innerHtmlError";
		}
		
		String cookie = (String) session.getAttribute(SessionKey.STUDENT_COOKIE+sNo);
		if(cookie==null){
			request.setAttribute("message", "登录教务网超时，请重新登录教务网!");
			return "innerHtmlError";
		}
		CampusArea campus  = new CampusArea();
		campus.setId(Integer.valueOf(campusId));
		Term term = (Term) session.getAttribute("term");
		if(term == null){
			term= termService.getCurrentTerm();
		}
		try {
			PublicCourseOption publicCourseOption = null;
			publicCourseOption =  new FetchHelper().fetchPublicCourseOptionSyn(cookie, campus , term.getNo(), value, skbjval);
			request.setAttribute("option", publicCourseOption);
		} catch (FetchTimeoutException e) {
			request.setAttribute("message", "获取数据超时！");
			return "innerHtmlError";
		}
		return "courseOption";
	}
	
	public IPublicCourseOptionService getPublicCourseOptionService() {
		return publicCourseOptionService;
	}
	public void setPublicCourseOptionService(IPublicCourseOptionService publicCourseOptionService) {
		this.publicCourseOptionService = publicCourseOptionService;
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
	public ICampusAreaService getCampusAreaService() {
		return campusAreaService;
	}
	public void setCampusAreaService(ICampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}
	public IMyCourseSchemeService getCourseSchemeService() {
		return courseSchemeService;
	}
	public void setCourseSchemeService(IMyCourseSchemeService courseSchemeService) {
		this.courseSchemeService = courseSchemeService;
	}
	
}
