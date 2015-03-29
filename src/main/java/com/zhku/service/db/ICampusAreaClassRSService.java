package com.zhku.service.db;

import com.zhku.bean.CampusAreaClassRS;

public interface ICampusAreaClassRSService {
	
	public void addCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public void deleteCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public void updateCampusAreaClassRS(CampusAreaClassRS campusAreaClassRS);
	public CampusAreaClassRS getCampusAreaClassRSByClassNo(String classNo);
}
