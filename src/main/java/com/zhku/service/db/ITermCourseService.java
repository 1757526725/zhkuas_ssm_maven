package com.zhku.service.db;

import java.util.List;
import java.util.Map;

import com.zhku.bean.TermCourse;
import com.zhku.module.fetchData.bo.TermCoursePageBo;

public interface ITermCourseService { 
	public TermCourse addTermCourse(TermCourse termCourse);
	public void deleteTermCourse(TermCourse termCourse);
	public void updateTermCourse(TermCourse termCourse);
	public List<TermCourse> getTermCourses();
	public TermCourse getTermCourseById(int xqid);
	public List<TermCourse> getTermCoursesByTermNo(String termNo);
	public TermCourse findTermCourseByConditions(TermCourse termCourse);
}
