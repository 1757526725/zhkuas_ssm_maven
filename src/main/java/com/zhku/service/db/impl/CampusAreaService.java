package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.CampusArea;
import com.zhku.dao.CampusAreaMapper;
import com.zhku.service.db.ICampusAreaService;
@Service("campusAreaService")
public class CampusAreaService implements ICampusAreaService {
	@Autowired
	private CampusAreaMapper campusAreaMapper;
	public void addCampusArea(CampusArea campusArea) {
		campusAreaMapper.addCampusArea(campusArea);
	}

	@Override
	public void deleteCampusArea(CampusArea campusArea) {
		campusAreaMapper.deleteCampusArea(campusArea);
	}

	@Override
	public void updateCampusArea(CampusArea campusArea) {
		campusAreaMapper.updateCampusArea(campusArea);
	}

	@Override
	public CampusArea getCampusAreaById(int compusId) {
		return campusAreaMapper.getCampusAreaById(compusId);
	}

	@Override
	public List<CampusArea> getCampusAreas() {
		return campusAreaMapper.getCampusAreas();
	}

	public CampusAreaMapper getCampusArea() {
		return campusAreaMapper;
	}

	public void setCampusArea(CampusAreaMapper campusAreaMapper) {
		this.campusAreaMapper = campusAreaMapper;
	}

	@Override
	public List<CampusArea> getCampusAreasWithSubData() {
//		String key = "getCampusAreasWithSubData";
//		Object obj = CacheUtils.get(key);
//		if(obj!=null){
//			return (List<CampusArea>) obj;
//		}
		return campusAreaMapper.getCampusAreasWithSubData();
	}

	@Override
	public CampusArea getCampusAreaWithBuildingListById(int campusId) {
		return campusAreaMapper.getCampusAreaWithBuildingListById(campusId);
	}

}
