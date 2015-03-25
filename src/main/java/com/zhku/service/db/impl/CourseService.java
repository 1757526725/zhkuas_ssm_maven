package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.Course;
import com.zhku.dao.CourseMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.ICourseService;
@Service("courseService")
public class CourseService implements ICourseService {
	@Autowired
	private CourseMapper courseMapper;
	
	@Override
	public void addCourse(Course course) throws ObjectExistsException {
		Course dbCourse = courseMapper.getCourseByCno(course.getNo());
		if(dbCourse!=null){
			throw new ObjectExistsException();
		}else{
			courseMapper.addCourse(course);
		}
	}

	@Override
	public void deleteCourse(Course course) {
		courseMapper.deleteCourse(course);
	}

	@Override
	public void updateCourse(Course course) {
		courseMapper.updateCourse(course);
	}

	@Override
	public Course getCourseById(int cid) {
		return courseMapper.getCourseById(cid);
	}

	@Override
	public List<Course> getCourses() {
		return courseMapper.getCourses();
	}

	@Override
	public Course getCourseByCno(String cNo) {
		return courseMapper.getCourseByCno(cNo);
	}

	@Override
	public Course getCourseByCnameNo(String cNameNo) {
		return courseMapper.getCourseByCnameNo(cNameNo);
	}

	@Override
	public Course getCourseWitchDetailByCno(String cNo) {
		return courseMapper.getCourseWitchDetailByCno(cNo);
	}

	public CourseMapper getCourseMapper() {
		return courseMapper;
	}

	public void setCourseMapper(CourseMapper courseMapper) {
		this.courseMapper = courseMapper;
	}

	@Override
	public List<Course> getCourses(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize,needCountTotal);
		return courseMapper.getCourses();
	}

	
}
