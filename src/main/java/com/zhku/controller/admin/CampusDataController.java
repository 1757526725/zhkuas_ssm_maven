package com.zhku.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.CampusArea;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.ICampusAreaService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/admin/school")
public class CampusDataController {
	@Autowired
	private ICampusAreaService campusAreaService ;
	
	@RequestMapping(value="/campuses")
	public String show(HttpServletRequest request){
		List<CampusArea> campuses = campusAreaService.getCampusAreasWithSubData();
		request.setAttribute("campuses", campuses);
		return "admin_campus";
	}
	@ResponseBody
	@RequestMapping(value="/campuses",method=RequestMethod.POST)
	public Map<String,Object> saveFetchDataToDb(HttpSession session){
		//从session 获取到 数据
		Object sessionOb = session.getAttribute(SessionKey.FETCH_CAMPUS.toString());
		if(sessionOb!=null){
			List<CampusArea> list= (List<CampusArea>) sessionOb;
			if(list.size()>0){
				//从数据库读取现有的数据
				List<CampusArea> campusesDb = campusAreaService.getCampusAreas();
				int updataCount=0;
				int addCount=0;
				for(CampusArea campus:list){
					int campusId = campus.getId();
					String campusName = campus.getName();
					boolean  isExit=false;
					for(CampusArea campusDb:campusesDb){
						if(campusDb.getId()==campusId){
							isExit =true;
							if(campusDb.getName().equals(campusName)){
								break;
							}else{
								//更新现有的数据
								campusAreaService.updateCampusArea(campus);
								updataCount++;
								break;
							}
						}
					}
					if(!isExit){
						//添加到数据库
						campusAreaService.addCampusArea(campus);
						addCount++;
					}
					
				}
				Map<String,Integer> result = new HashMap<String,Integer>();
				result.put("updataCount",updataCount);
				result.put("addCount",addCount);
				return WebUtils.webJsonResult(result);
			}else{
				return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
			} 
		}else{
			return WebUtils.webJsonError(Error.DATA_LIST_IS_EMPTY);
		}
	}
	@ResponseBody
	@RequestMapping(value="/campuses",method=RequestMethod.PUT)
	public Map<String,Object> updateSyc(HttpSession session){
		try {
			List list =new FetchHelper().fetchCampusAreas();
			session.setAttribute(SessionKey.FETCH_CAMPUS.toString(), list);
			return WebUtils.webJsonResult(list);
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Error.FETCH_TIME_OUT);
		}
	}
	public ICampusAreaService getCampusAreaService() {
		return campusAreaService;
	}

	public void setCampusAreaService(ICampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}
}
