package com.zhku.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhku.bean.CampusArea;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseType;
import com.zhku.bean.Term;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.IPublicCourseService;
import com.zhku.service.db.IPublicCourseTypeService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebConfigUtils;

@Controller
@RequestMapping("/main")
public class PublicCourseController {
	@Autowired
	private IPublicCourseService publicCourseService;
	@Autowired
	private ITermService termService;
	@Autowired
	private ICampusAreaService campusAreaService;
	@Autowired
	private IPublicCourseTypeService publicCourseTypeService;
	@RequestMapping("/campus/course")
	public String show(HttpServletRequest request){
		String termNo=WebConfigUtils.getValue("currentTermNo");
		int campusId = 1;
		CampusArea campus = campusAreaService.getCampusAreaById(campusId);
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(termNo, campusId);
		List<Term> terms = termService.getAvailabelTerms();
		for(Term term:terms){
			if(term.isCurrent()){
				request.setAttribute("term", term);
			}
		}
		request.setAttribute("terms", terms);
		request.setAttribute("publicCourses", publicCourses);
		request.setAttribute("campus", campus);
		return "public_course";
	}
	@RequestMapping("/campus/{campusId}/course")
	public String show(@PathVariable Integer campusId,HttpServletRequest request){
		String termNo=WebConfigUtils.getValue("currentTermNo");
		CampusArea campus = campusAreaService.getCampusAreaById(campusId);
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(termNo, campusId);
		List<Term> terms = termService.getAvailabelTerms();
		for(Term term:terms){
			if(term.isCurrent()){
				request.setAttribute("term", term);
			}
		}
		request.setAttribute("terms", terms);
		request.setAttribute("publicCourses", publicCourses);
		request.setAttribute("campus", campus);
		return "public_course";
	}
	@RequestMapping("/campus/{campusId}/course/term/{termNo}")
	public String show(@PathVariable Integer campusId,@PathVariable String termNo,HttpServletRequest request){
		CampusArea campus = campusAreaService.getCampusAreaById(campusId);
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusId(termNo, campusId);
		List<Term> terms = termService.getAvailabelTerms();
		Term term = null;
		for(Term perTerm:terms){
			if(perTerm.getNo().equals(termNo)){
				term =perTerm;
			}
		}
		
		if(term==null){
			term = termService.getTermByNo(termNo);
		}
		request.setAttribute("term", term);
		request.setAttribute("terms", terms);
		request.setAttribute("publicCourses", publicCourses);
		request.setAttribute("campus", campus);
		return "public_course";
	}
	
	@RequestMapping("/campus/{campusId}/course/term/{termNo}/type/{typeId}")
	public String show(@PathVariable Integer campusId,@PathVariable String termNo,@PathVariable Integer typeId,HttpServletRequest request){
		CampusArea campus = campusAreaService.getCampusAreaById(campusId);
		PublicCourseType publicCourseType = publicCourseTypeService.getPublicCourseTypeById(typeId);
		List<PublicCourse> publicCourses = publicCourseService.getPublicCoursesByTermNoAndCampusIdAndTypeId(termNo, campusId, typeId);
		List<Term> terms = termService.getAvailabelTerms();
		Term term = null;
		for(Term perTerm:terms){
			if(perTerm.getNo().equals(termNo)){
				term =perTerm;
			}
		}
		
		if(term==null){
			term = termService.getTermByNo(termNo);
		}
		request.setAttribute("term", term);
		request.setAttribute("terms", terms);
		request.setAttribute("publicCourses", publicCourses);
		request.setAttribute("campus", campus);
		request.setAttribute("publicCourseType", publicCourseType);
		return "public_course";
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
	public IPublicCourseTypeService getPublicCourseTypeService() {
		return publicCourseTypeService;
	}
	public void setPublicCourseTypeService(IPublicCourseTypeService publicCourseTypeService) {
		this.publicCourseTypeService = publicCourseTypeService;
	}
}
