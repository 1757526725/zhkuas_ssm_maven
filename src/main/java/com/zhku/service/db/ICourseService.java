package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Course;
import com.zhku.exception.ObjectExistsException;

public interface ICourseService {
	public void addCourse(Course course) throws ObjectExistsException;
	public void deleteCourse(Course course);
	public void updateCourse(Course course);
	public Course getCourseById(int cid);
	public List<Course> getCourses();
	public List<Course> getCourses(int pageNum,int pageSize,boolean needCountTotal);
	public Course getCourseByCno(String cNo);
	public Course getCourseByCnameNo(String cNameNo);
	public Course getCourseWitchDetailByCno(String cNo);
}
