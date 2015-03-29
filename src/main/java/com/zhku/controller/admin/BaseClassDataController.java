package com.zhku.controller.admin;

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

import com.zhku.bean.Academy;
import com.zhku.bean.BaseClass;
import com.zhku.bean.CampusAreaClassRS;
import com.zhku.bean.Term;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.ICampusAreaClassRSService;
import com.zhku.service.db.IOrganizationService;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.PageSize;

@Controller
@RequestMapping("/admin/school")
public class BaseClassDataController {
	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private ITermService termService;
	@Autowired
	private ICampusAreaClassRSService campusAreaClassRSService;
	@ResponseBody
	@RequestMapping(value="/campusAndclass/rs",method = RequestMethod.POST)
	public Map<String,Object> saveCampusAreaClassRS(HttpSession session,String classNos,Integer campusId){
		if(classNos==null || campusId==null){
			return WebUtils.webJsonError(Error.PARAMETER_ERROR);
		}
		int count =0;
		String[] classNoArray = classNos.split(",");
		for(String classNo:classNoArray){
			CampusAreaClassRS campusAreaClassRS = new CampusAreaClassRS();
			campusAreaClassRS.setCampusId(campusId);
			campusAreaClassRS.setClassNo(classNo);
			CampusAreaClassRS dbItem = campusAreaClassRSService.getCampusAreaClassRSByClassNo(classNo);
			if(dbItem==null){
				BaseClass dbClass = baseClassService.getBaseClassByClassNo(classNo);
				if(dbClass!=null){
					campusAreaClassRSService.addCampusAreaClassRS(campusAreaClassRS);
					count++;
				}
			}else{
				if(!dbItem.getCampusId().equals(campusId)){
					dbItem.setCampusId(campusId);
					campusAreaClassRSService.updateCampusAreaClassRS(dbItem);
				}
				count++;
			}
		}
		Map<String,Object> result = new HashMap<String,Object> ();
		result.put("success", count);
		result.put("fail", classNoArray.length-count);
		return WebUtils.webJsonResult(result);
	}
	
	@RequestMapping(value="/classes")
	public String show(HttpSession session ,HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampus(1, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if(session.getAttribute("admin_class_acadmeys")==null){
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys",acadmeylist);
		}
		//填充当学年信息
		if(session.getAttribute("admin_class_years")==null){
			Term term = termService.getCurrentTerm();
			int year = Integer.parseInt(term.getNo().substring(0, 4));
			List<Integer> years = new ArrayList <Integer> ();
			StringBuffer sb = new StringBuffer();
	 		for(int i=year-4;i<=year+1;i++){
	 			years.add(i);
	 			sb.append(i).append(",");
			}
	 		sb.deleteCharAt(sb.length()-1);
	 		session.setAttribute("grade_all", sb.toString());
	 		session.setAttribute("admin_class_years", years);
		}
		
		return "admin_class";
	}
	@RequestMapping(value="/classes/page/{pageNum}")
	public String showPage(@PathVariable Integer pageNum ,HttpSession session ,HttpServletRequest request){
		List<BaseClass> list = baseClassService.getBaseClassesWithMajorAndCampus(pageNum, PageSize.ADMIN_CLASS.getSize(), true);
		request.setAttribute("classes", list);
		if(session.getAttribute("admin_class_acadmeys")==null){
			List<Academy> acadmeylist = organizationService.getAcademys();
			session.setAttribute("admin_class_acadmeys",acadmeylist);
		}
		//填充当学年信息
		if(session.getAttribute("admin_class_years")==null){
			Term term = termService.getCurrentTerm();
			int year = Integer.parseInt(term.getNo().substring(0, 4));
			List<Integer> years = new ArrayList <Integer> ();
			StringBuffer sb = new StringBuffer();
	 		for(int i=year-4;i<=year+1;i++){
	 			years.add(i);
	 			sb.append(i).append(",");
			}
	 		sb.deleteCharAt(sb.length()-1);
	 		session.setAttribute("grade_all", sb.toString());
	 		session.setAttribute("admin_class_years", years);
		}
		return "admin_class";
	}
	
	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}
	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
	public IOrganizationService getOrganizationService() {
		return organizationService;
	}
	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

	public ICampusAreaClassRSService getCampusAreaClassRSService() {
		return campusAreaClassRSService;
	}

	public void setCampusAreaClassRSService(ICampusAreaClassRSService campusAreaClassRSService) {
		this.campusAreaClassRSService = campusAreaClassRSService;
	}
}
