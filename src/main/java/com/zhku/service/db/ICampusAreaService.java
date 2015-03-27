package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.CampusArea;

public interface ICampusAreaService {
	public void addCampusArea(CampusArea campusArea);
	public void deleteCampusArea(CampusArea campusArea);
	public void updateCampusArea(CampusArea campusArea);
	public CampusArea getCampusAreaById(int compusId);
	public List<CampusArea> getCampusAreas();
	/**
	 * 获取 到 完整的 校区 bean 带 教学楼列表 和 班级 列表
	 * @return
	 */
	public List<CampusArea> getCampusAreasWithSubData();
	public CampusArea getCampusAreaWithBuildingListById(int campusId);
}
