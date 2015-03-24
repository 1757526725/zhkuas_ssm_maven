package com.zhku.controller.data;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Major;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.IClassroomService;
import com.zhku.service.db.IMajorService;
import com.zhku.service.db.IOrganizationService;
import com.zhku.service.db.ISchoolBuildingService;
import com.zhku.tools.cache.impl.BaeMemcacheImpl;
import com.zhku.utils.WebUtils;

@Controller
@RequestMapping(value="/data")
public class SchoolInfoDataController {
	@Autowired
	private ICampusAreaService campusAreaService;
	@Autowired
	private ISchoolBuildingService schoolBuildingService;
	@Autowired 
	private IClassroomService classroomService;
	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private IMajorService majorService;
	@ResponseBody
	@RequestMapping("/campuses/json")
	public Map<String,Object> getCampusJsonList(){
		return WebUtils.webJsonResult(campusAreaService.getCampusAreas());
	}
	@ResponseBody
	@RequestMapping("/buildings/json")
	public Map<String,Object> getBuildingJsonList(){
		return WebUtils.webJsonResult(schoolBuildingService.getSchoolBuildings());
	}
	@ResponseBody
	@RequestMapping("/classrooms/json")
	public Map<String,Object> getClassroomJsonList(){
		return WebUtils.webJsonResult(classroomService.getClassrooms());
	}
	@ResponseBody
	@RequestMapping("/acamdeys/json")
	public Map<String,Object> getAcamdeyList(){
		return WebUtils.webJsonResult(organizationService.getAcademys());
	}
	@ResponseBody
	@RequestMapping("/classes/json")
	public Map<String,Object> getClassesJsonList(){
		return WebUtils.webJsonResult(baseClassService.getBaseClasses());
	}
	@ResponseBody
	@RequestMapping("/major/{majorNo}/class/json")
	public Map<String,Object> getClassesJsonList(@PathVariable String majorNo){
		List<BaseClass> baseclasses = baseClassService.getBaseClassesByMajorNo(majorNo);
		return WebUtils.webJsonResult(baseclasses);
	}
	@ResponseBody
	@RequestMapping(value = "/classes/json",method = RequestMethod.POST)
	public Map<String,Object> getClassesJsonListByKeyWord(String keyword){
		return WebUtils.webJsonResult(baseClassService.getBaseClassesByClassName(keyword));
	}
	
	public ICampusAreaService getCampusAreaService() {
		return campusAreaService;
	}
	public void setCampusAreaService(ICampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}
	public ISchoolBuildingService getSchoolBuildingService() {
		return schoolBuildingService;
	}
	public void setSchoolBuildingService(ISchoolBuildingService schoolBuildingService) {
		this.schoolBuildingService = schoolBuildingService;
	}
	public IClassroomService getClassroomService() {
		return classroomService;
	}
	public void setClassroomService(IClassroomService classroomService) {
		this.classroomService = classroomService;
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
	public IMajorService getMajorService() {
		return majorService;
	}
	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}
	
}
