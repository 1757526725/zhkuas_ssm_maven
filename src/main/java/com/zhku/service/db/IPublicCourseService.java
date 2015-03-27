package com.zhku.service.db;

import java.util.List;
import java.util.Map;

import com.zhku.bean.PublicCourse;
import com.zhku.bean.TermCourse;
import com.zhku.exception.ObjectExistsException;

public interface IPublicCourseService {
	public void addPublicCourse(PublicCourse publicCourse) throws ObjectExistsException;
	public void deletePublicCourse(PublicCourse publicCourse);
	public List<PublicCourse> getPublicCoursesByTermNoAndCampusId(String termNo,int campusId);
	public PublicCourse getPublicCourseById(int pcid);
	public List<PublicCourse> getPublicCoursesByTermNoAndCampusIdAndTypeId(String termNo,int campusId,int typeId);
	public PublicCourse getPublicCourseByTermCourseAndCompusId(TermCourse termCourse, String compusId);
	public List<PublicCourse> getPublicCoursesBytermNoAndCompusIdAndCno(String termNo, int compustId, String cNo);
}
