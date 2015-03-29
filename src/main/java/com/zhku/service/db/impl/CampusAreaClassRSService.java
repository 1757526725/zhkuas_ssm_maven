package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.CampusAreaClassRS;
import com.zhku.dao.CampusAreaClassRSMapper;
import com.zhku.service.db.ICampusAreaClassRSService;

@Service("campusAreaClassRSService")
public class CampusAreaClassRSService implements ICampusAreaClassRSService {
	@Autowired
	private CampusAreaClassRSMapper campusAreaClassRSMapper;
	@Override
	public void addCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS) {
		campusAreaClassRSMapper.addCampusAreaClassRS(campusAreaClassRS);
	}

	@Override
	public void deleteCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS) {
		campusAreaClassRSMapper.deleteCampusAreaClassRS(campusAreaClassRS);
	}

	@Override
	public void updateCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS) {
		campusAreaClassRSMapper.updateCampusAreaClassRS(campusAreaClassRS);
	}



	public CampusAreaClassRSMapper getCampusAreaClassRSMapper() {
		return campusAreaClassRSMapper;
	}

	public void setCampusAreaClassRSMapper(CampusAreaClassRSMapper campusAreaClassRSMapper) {
		this.campusAreaClassRSMapper = campusAreaClassRSMapper;
	}

	@Override
	public CampusAreaClassRS getCampusAreaClassRSByClassNo(String classNo) {
		return campusAreaClassRSMapper.getCampusAreaClassRSByClassNo(classNo);
	}

}
