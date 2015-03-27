package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.SchoolBuilding;
import com.zhku.dao.SchoolBuildingMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.ISchoolBuildingService;
@Service("schoolBuildingService")
public class SchoolBuildingService implements ISchoolBuildingService {
	@Autowired
	private SchoolBuildingMapper schoolBuildingMapper;
	@Override
	public void addSchoolBuilding(SchoolBuilding schoolBuilding) throws ObjectExistsException {
		SchoolBuilding sb=schoolBuildingMapper.getSchoolBuildingByNo(schoolBuilding.getNo());
		if(sb!=null){
			throw new ObjectExistsException();
		}else{
			schoolBuildingMapper.addSchoolBuilding(schoolBuilding);
		}
	}

	@Override
	public void deleteSchoolBuilding(SchoolBuilding schoolBuilding) {
		schoolBuildingMapper.deleteSchoolBuilding(schoolBuilding);
	}

	@Override
	public void updateSchoolBuilding(SchoolBuilding schoolBuilding) {
		schoolBuildingMapper.updateSchoolBuilding(schoolBuilding);
	}

	@Override
	public SchoolBuilding getSchoolBuildingById(int id) {
		return schoolBuildingMapper.getSchoolBuildingById(id);
	}

	@Override
	public SchoolBuilding getSchoolBuildingByNo(String no) {
		return schoolBuildingMapper.getSchoolBuildingByNo(no);
	}

	@Override
	public SchoolBuilding getSchoolBuildingByName(String name) {
		return schoolBuildingMapper.getSchoolBuildingByName(name);
	}

	@Override
	public List<SchoolBuilding> getSchoolBuildings() {
		return schoolBuildingMapper.getSchoolBuildings();
	}

	public SchoolBuildingMapper getSchoolBuildingMapper() {
		return schoolBuildingMapper;
	}

	public void setSchoolBuildingMapper(SchoolBuildingMapper schoolBuildingMapper) {
		this.schoolBuildingMapper = schoolBuildingMapper;
	}

	@Override
	public List<SchoolBuilding> getSchoolBuildingsByCompusId(int compusId) {
		return schoolBuildingMapper.getSchoolBuildingsByCompusId(compusId);
	}

	@Override
	public List<SchoolBuilding> getSchoolBuildingsWithClassroomList() {
		return schoolBuildingMapper.getSchoolBuildingsWithClassroomList();
	}

}
