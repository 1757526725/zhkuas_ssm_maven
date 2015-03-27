package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.SchoolBuilding;
import com.zhku.exception.ObjectExistsException;

public interface ISchoolBuildingService {
	public void addSchoolBuilding(SchoolBuilding schoolBuilding) throws ObjectExistsException;

	public void deleteSchoolBuilding(SchoolBuilding schoolBuilding);

	public void updateSchoolBuilding(SchoolBuilding schoolBuilding);

	public SchoolBuilding getSchoolBuildingById(int id);

	public SchoolBuilding getSchoolBuildingByNo(String no);

	public SchoolBuilding getSchoolBuildingByName(String name);

	public List<SchoolBuilding> getSchoolBuildings();

	public List<SchoolBuilding> getSchoolBuildingsByCompusId(int compusId);

	public List<SchoolBuilding> getSchoolBuildingsWithClassroomList();

}
