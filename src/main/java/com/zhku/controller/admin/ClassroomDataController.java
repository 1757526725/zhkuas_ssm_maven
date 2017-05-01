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

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.Classroom;
import com.zhku.bean.SchoolBuilding;
import com.zhku.exception.ObjectExistsException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IClassroomService;
import com.zhku.service.db.ISchoolBuildingService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.PageSize;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/admin/school")
public class ClassroomDataController {

	@Autowired
	private ISchoolBuildingService schoolBuildingService;
	@Autowired
	private IClassroomService classroomService;
	@RequestMapping("/classrooms")
	public String show(HttpSession session ,HttpServletRequest request){
		genBuildingsInSession(session);
		List<Classroom> classrooms = classroomService.getClassrooms(1,PageSize.ADMIN_CLASSROOM.getSize(),true);
		request.setAttribute("classrooms",classrooms);
		return "admin_classroom";
	}
	private void genBuildingsInSession(HttpSession session) {
		if(session.getAttribute("buildings")==null){
			List<SchoolBuilding> sbs = schoolBuildingService.getSchoolBuildings();
			session.setAttribute("buildings", sbs);
		}
	}
	@RequestMapping("/classrooms/page/{pageNum}")
	public String show(HttpSession session,@PathVariable Integer pageNum,HttpServletRequest request){
		genBuildingsInSession(session);
		List<Classroom> classrooms = classroomService.getClassrooms(pageNum,PageSize.ADMIN_CLASSROOM.getSize(),true);
		request.setAttribute("classrooms",classrooms);
		return "admin_classroom";
	}
	@RequestMapping(value="/building/{buildingNo}/classrooms",method=RequestMethod.GET)
	public String show(HttpSession session,HttpServletRequest request,@PathVariable String buildingNo){
		genBuildingsInSession(session);
		List<Classroom> classrooms = classroomService.getClassroomsBySchoolBuildingNo(buildingNo);
		request.setAttribute("classrooms",classrooms);
		return "admin_classroom";
	}
	@ResponseBody
	@RequestMapping(value="/classrooms/progress",method=RequestMethod.GET)
	public Map<String,Object> getProgress(HttpSession session){
		Map<String,Integer> result = new HashMap<String,Integer>();
		Object ob = session.getAttribute(SessionKey.PROGRESS.toString());
		Integer progress =null;
		if(ob==null){
			progress =0;
		}else{
			progress = (Integer) ob;
		}
		result.put("progress", progress);
		return WebUtils.webJsonResult(result);
	}
	
	@ResponseBody
	@RequestMapping(value="/classrooms",method=RequestMethod.POST)
	public Map<String,Object> saveToDb(HttpSession session,Integer step){
		//先从 session中合并 数据
		switch (step) {
		case 1:
			List<SchoolBuilding> buildings = schoolBuildingService.getSchoolBuildings();
			List<Classroom>  classrooms = new ArrayList<Classroom>();
			for(SchoolBuilding building:buildings){
				Object ob = session.getAttribute(SessionKey.FETCH_CLASSROOM.toString()+building.getNo());
				if(ob!=null){
					classrooms.addAll((List<Classroom>)ob);
				}
				session.removeAttribute(SessionKey.FETCH_CLASSROOM.toString()+building.getNo()); //移除session中的临时变量
			}
			session.setAttribute(SessionKey.FETCH_CLASSROOM_DATA_LIST.toString(), classrooms);
			Map<String ,Integer> result = new HashMap<String,Integer>();
			result.put("done", classrooms.size());
			return WebUtils.webJsonResult(result);
		case 2: //保存到DB
			Object ob = session.getAttribute(SessionKey.FETCH_CLASSROOM_DATA_LIST.toString());
			if(ob==null){
				return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
			}
			classrooms = (List<Classroom>) ob;
			int updateCount=0;
			int addCount=0;
			Map<String ,Integer> dbresult = new HashMap<String,Integer>();
			for(int i = 0;i<classrooms.size();++i){
				Classroom classroom =classrooms.get(i);
				try {
					classroomService.addClassroom(classroom);
					addCount++;
				} catch (ObjectExistsException e) {
//					e.printStackTrace();
					updateCount++;
				}
				session.setAttribute(SessionKey.PROGRESS.toString(), i+1);
			}
			dbresult.put("updateCount", updateCount);
			dbresult.put("addCount", addCount);
			session.removeAttribute(SessionKey.FETCH_CLASSROOM_DATA_LIST.toString());
			return WebUtils.webJsonResult(dbresult);
		default:
			//清除session的值
			break;
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/building/{buildingNo}/classrooms",method=RequestMethod.PUT)
	public Map<String,Object> updateSyc(@PathVariable String buildingNo ,HttpSession session){
		if(buildingNo!=null){
			SchoolBuilding schoolBuilding = new SchoolBuilding();
			schoolBuilding.setNo(buildingNo);
			FetchHelper fetchHelper = new FetchHelper();
			List<Classroom> classrooms =null;
			try {
				classrooms=fetchHelper.fetchClassrooms(schoolBuilding);
				session.setAttribute(SessionKey.FETCH_CLASSROOM.toString()+schoolBuilding.getNo(), classrooms); //将数据暂时放到session中
			} catch (FetchTimeoutException e) {
				return WebUtils.webJsonError(Error.FETCH_TIME_OUT);
			}
		}else{
			return WebUtils.webJsonError(Error.PARAMETER_ERROR);
		}
		return WebUtils.webJsonResult("done");
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
}
