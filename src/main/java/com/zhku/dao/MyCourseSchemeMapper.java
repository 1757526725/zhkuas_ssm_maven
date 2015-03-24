package com.zhku.dao;

import java.util.List;
import java.util.Map;

import com.zhku.bean.MyCourseScheme;

public interface MyCourseSchemeMapper {
	public void addMyCourseScheme(MyCourseScheme myCourseScheme);

	public void deleteMyCourseScheme(MyCourseScheme myCourseScheme);

	public void updateMyCourseScheme(MyCourseScheme myCourseScheme);

	/**
	 * @param params
	 *            key:termNO,uid;
	 */
	public List<MyCourseScheme> getMyCourseSchemesByTermNoAndUid(
			Map<String, String> params);

	public MyCourseScheme getMyCourseSchemeById(int msid);

	public List<MyCourseScheme> getMyCourseSchemesByUid(int uid);
	public MyCourseScheme findMyCourseScheme(MyCourseScheme myCourseScheme);
	/**
	 * 
	 * @param params key msid ,orderNum
	 */
	public void updateOrdeNumOfMyCourseScheme(Map<String,String> params);
	public void deleteSchemesByUid(int uid);
}
