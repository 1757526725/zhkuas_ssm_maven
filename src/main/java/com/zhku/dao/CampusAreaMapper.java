package com.zhku.dao;

import java.util.List;

import com.zhku.bean.CampusArea;

public interface CampusAreaMapper {
	public void addCampusArea(CampusArea campusArea);
	public void deleteCampusArea(CampusArea campusArea);
	public void updateCampusArea(CampusArea campusArea);
	public CampusArea getCampusAreaById(int campusId);
	public List<CampusArea> getCampusAreas();
	public List<CampusArea> getCampusAreasWithSubData();
	public CampusArea getCampusAreaWithBuildingListById(int campusId);
}
