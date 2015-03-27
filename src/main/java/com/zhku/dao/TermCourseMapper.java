package com.zhku.dao;

import java.util.List;

import com.zhku.bean.TermCourse;

public interface TermCourseMapper { 
	public void addTermCourse(TermCourse termCourse);
	public void deleteTermCourse(TermCourse termCourse);
	public void updateTermCourse(TermCourse termCourse);
	public List<TermCourse> getTermCourses();
	public TermCourse getTermCourseById(int xqid);
	public List<TermCourse> getTermCoursesByTermNo(String termNo);
	public TermCourse findTermCourseByConditions(TermCourse termCourse);
}
