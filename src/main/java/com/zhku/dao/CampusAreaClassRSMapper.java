package com.zhku.dao;

import com.zhku.bean.CampusAreaClassRS;

public interface CampusAreaClassRSMapper {
	
	public void addCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public void deleteCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public void updateCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public CampusAreaClassRS getCampusAreaClassRSByClassNo(String classNo);
}
