package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Course;

public interface CourseMapper {
	public void addCourse(Course course);
	public void deleteCourse(Course course);
	public void updateCourse(Course course);
	public Course getCourseById(int cid);
	public List<Course> getCourses();
	public Course getCourseByCno(String cNo);
	public Course getCourseByCnameNo(String cNameNo);
	public Course getCourseWitchDetailByCno(String cNo);
}
