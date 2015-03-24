package com.zhku.dao;

import java.util.List;
import java.util.Map;

import com.zhku.bean.ClassCourse;

public interface ClassCourseMapper {
	
	public void addClassCourse(ClassCourse classCourse);
	
	public void deleteClassCourse(ClassCourse classCourse);
	
	public void updateClassCourse(ClassCourse classCourse);
	
	public ClassCourse getClassCourseById(int ccid);
	
	public List<ClassCourse> getClassCourses();

	public ClassCourse findClassCourse(Map<String, String> params); 
}
