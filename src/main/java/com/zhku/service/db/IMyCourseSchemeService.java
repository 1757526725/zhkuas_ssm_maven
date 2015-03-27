package com.zhku.service.db;

import java.util.List;
import java.util.Map;

import com.zhku.bean.MyCourseScheme;

public interface IMyCourseSchemeService {
	public void addMyCourseScheme(MyCourseScheme myCourseScheme);
	public void deleteMyCourseScheme(MyCourseScheme myCourseScheme);
	public void updateMyCourseScheme(MyCourseScheme myCourseScheme);
	public List<MyCourseScheme> getMyCourseSchemesByTermNoAndUid(String termNo,int uid);
	public MyCourseScheme getMyCourseSchemeById(int id);
	public List<MyCourseScheme> getMyCourseSchemesByUid(int uid);
	public MyCourseScheme findMyCourseScheme(MyCourseScheme myCourseScheme);
	public void updateOrdeNumOfMyCourseScheme(MyCourseScheme myCourseScheme,int orderNum	);
	public void deleteSchemesByUid(int uid);

}
