package com.zhku.dao;

import java.util.List;
import java.util.Map;

import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseType;

public interface PublicCourseMapper {
	public void addPublicCourse(PublicCourse publicCourse);
	public void deletePublicCourse(PublicCourse publicCourse);
	public List<PublicCourse> getPublicCoursesBytermNoAndCompusId(Map<String,String> params);
	public PublicCourse getPublicCourseById(int pcid);
	public List<PublicCourse> getPublicCourseByCourseType(PublicCourseType publicCourseType);
	public List<PublicCourse> getPublicCoursesBytermNoAndCompusIdAndTypeId(
			Map<String, String> params);
	public List<PublicCourse> getPublicCoursesBytermNoAndCompusIdAndCno(Map<String, String> params);

	public PublicCourse getPublicCourseByTermCourseAndCompusId(
			Map<String, String> params);
}
