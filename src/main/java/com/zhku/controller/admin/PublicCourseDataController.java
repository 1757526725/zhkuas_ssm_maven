package com.zhku.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.CampusArea;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.Term;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.IPublicCourseService;
import com.zhku.service.db.ITermService;
/**
 * 公选课数据管理
 * @author JackCan
 *
 */
@Controller
@RequestMapping("/admin/course")
public class PublicCourseDataController {
	@Autowired
	private IPublicCourseService publicCourseService;
	@Autowired
	private ITermService termService;
	@Autowired
	private ICampusAreaService campusAreaService;
	@RequestMapping("/public")
	public String show(HttpServletRequest request){
		List<Term> terms = termService.getAvailabelTerms();
		Term currentTerm  = null;
		for(Term term:terms){
			if(term.isCurrent()){
				currentTerm = term;
				request.setAttribute("currentTerm", currentTerm);
				break;
			}	
		}
		request.setAttribute("terms", terms);
		int compusId = 1;
		CampusArea currentCampus = campusAreaService.getCampusAreaById(compusId );
		List<CampusArea> campuses = campusAreaService.getCampusAreas();
		request.setAttribute("campuses", campuses);
		request.setAttribute("currentCampus", currentCampus);
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(currentTerm.getNo(), currentCampus.getId());
		request.setAttribute("publicCourses", publicCourses);
		return "admin_public_course";
	}
	public IPublicCourseService getPublicCourseService() {
		return publicCourseService;
	}
	public void setPublicCourseService(IPublicCourseService publicCourseService) {
		this.publicCourseService = publicCourseService;
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
}
