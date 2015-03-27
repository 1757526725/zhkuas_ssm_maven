package com.zhku.dao;

import java.util.List;

import com.zhku.bean.SchoolBuilding;

public interface SchoolBuildingMapper {
	public void addSchoolBuilding(SchoolBuilding schoolBuilding);

	public void deleteSchoolBuilding(SchoolBuilding schoolBuilding);

	public void updateSchoolBuilding(SchoolBuilding schoolBuilding);

	public SchoolBuilding getSchoolBuildingById(int id);

	public SchoolBuilding getSchoolBuildingByNo(String no);

	public SchoolBuilding getSchoolBuildingByName(String name);
	
	public List<SchoolBuilding> getSchoolBuildings();
	public List<SchoolBuilding> getSchoolBuildingsByCompusId(int compusId);
	public List<SchoolBuilding> getSchoolBuildingsWithClassroomList();
}
