package com.zhku.dao;

import com.zhku.bean.CourseTeacherRS;

public interface CourseTeacherRSMapper {
	
	public void addCourseTeacherRS(CourseTeacherRS courseTeacherRS);
	public void deleteCourseTeacherRS(CourseTeacherRS courseTeacherRS);
	public void updateCourseTeacherRS(CourseTeacherRS courseTeacherRS);
	public CourseTeacherRS getCourseTeacherRSbyConditions(CourseTeacherRS courseTeacherRS);
}
