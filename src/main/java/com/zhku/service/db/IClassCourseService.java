package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.BaseClass;
import com.zhku.bean.ClassCourse;
import com.zhku.bean.TermCourse;

public interface IClassCourseService {
	
	public void addClassCourse(ClassCourse classCourse);
	
	public void deleteClassCourse(ClassCourse classCourse);
	
	public void updateClassCourse(ClassCourse classCourse);
	
	public ClassCourse getClassCourseById(int ccid);
	
	public List<ClassCourse> getClassCourses();

	public ClassCourse findClassCourse(BaseClass baseClass,TermCourse termCourse); 
}
