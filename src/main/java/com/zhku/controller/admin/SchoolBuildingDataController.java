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

import com.zhku.bean.CampusArea;
import com.zhku.bean.SchoolBuilding;
import com.zhku.exception.ObjectExistsException;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.service.db.ISchoolBuildingService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/admin/school")
public class SchoolBuildingDataController {

	@Autowired
	private ISchoolBuildingService schoolBuildingService;
	@Autowired
	private ICampusAreaService campusAreaService;
	@RequestMapping("/buildings")
	public String show(HttpServletRequest request){
		List<CampusArea> campuses = campusAreaService.getCampusAreas();
		List<SchoolBuilding> buildings = schoolBuildingService.getSchoolBuildingsWithClassroomList();
		request.setAttribute("campuses", campuses);
		request.setAttribute("buildings", buildings);
		return "admin_schoolBuilding";
	}
	@RequestMapping(value="/campus/{campusId}/buildings",method=RequestMethod.GET)
	public String show(HttpServletRequest request,@PathVariable Integer campusId){
		List<CampusArea> campuses = campusAreaService.getCampusAreas();
		CampusArea currentCampus = campusAreaService.getCampusAreaWithBuildingListById(campusId);
		List<SchoolBuilding> buildings = currentCampus.getBuildingList();
		request.setAttribute("campuses", campuses);
		request.setAttribute("currentCampus", currentCampus);
		request.setAttribute("buildings", buildings);
		return "admin_schoolBuilding";
	}
	@ResponseBody
	@RequestMapping(value="/buildings",method=RequestMethod.POST)
	public Map<String,Object> saveToDb(HttpSession session,Integer step){
		//先从 session中合并 数据
		switch (step) {
		case 1:
			List<CampusArea> campuses = campusAreaService.getCampusAreas();
			List<SchoolBuilding> buildings = new ArrayList<SchoolBuilding>();
			for(CampusArea campus:campuses){
				Object ob = session.getAttribute(SessionKey.FETCH_BUILDING.toString()+campus.getId());
				if(ob!=null){
					buildings.addAll((List<SchoolBuilding>)ob);
				}
				session.removeAttribute(SessionKey.FETCH_BUILDING.toString()+campus.getId()); //移除session中的临时变量
			}
			session.setAttribute(SessionKey.FETCH_BUILDING_DATA_LIST.toString(), buildings);
			Map<String ,Integer> result = new HashMap<String,Integer>();
			result.put("done", buildings.size());
			return WebUtils.webJsonResult(result);
		case 2: //保存到DB
			Object ob = session.getAttribute(SessionKey.FETCH_BUILDING_DATA_LIST.toString());
			if(ob==null){
				return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
			}
			buildings = (List<SchoolBuilding>) ob;
			int updateCount=0;
			int addCount=0;
			Map<String ,Integer> dbresult = new HashMap<String,Integer>();
			for(SchoolBuilding building:buildings){
				try {
					schoolBuildingService.addSchoolBuilding(building);
					addCount++;
				} catch (ObjectExistsException e) {
//					e.printStackTrace();
					updateCount++;
				}
			}
			dbresult.put("updateCount", updateCount);
			dbresult.put("addCount", addCount);
			session.removeAttribute(SessionKey.FETCH_BUILDING_DATA_LIST.toString());
			return WebUtils.webJsonResult(dbresult);
		default:
			//清除session的值
			break;
		}
		
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/campus/{id}/buildings",method=RequestMethod.PUT)
	public Map<String,Object> updateSyc(@PathVariable Integer id ,HttpSession session){
		if(id!=null){
			CampusArea campusArea = new CampusArea();
			campusArea.setId(id);
			FetchHelper fetchHelper = new FetchHelper();
			List<SchoolBuilding> buildings =null;
			try {
				buildings=fetchHelper.fetchSchoolBuildings(campusArea);
				session.setAttribute(SessionKey.FETCH_BUILDING.toString()+campusArea.getId(), buildings); //将数据暂时放到session中
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
	public ICampusAreaService getCampusAreaService() {
		return campusAreaService;
	}
	public void setCampusAreaService(ICampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}
}
