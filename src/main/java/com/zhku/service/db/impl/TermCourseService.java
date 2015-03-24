package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.CourseTeacherRS;
import com.zhku.bean.TermCourse;
import com.zhku.dao.CourseTeacherRSMapper;
import com.zhku.dao.TermCourseMapper;
import com.zhku.service.db.ITermCourseService;
@Service("termCourseService")
public class TermCourseService implements
		ITermCourseService {

	@Autowired
	private TermCourseMapper termCourseMapper;
	@Autowired
	private CourseTeacherRSMapper courseTeacherRSMapper;
	@Override
	public TermCourse addTermCourse(TermCourse termCourse) {
		CourseTeacherRS courseTeacherRS = new CourseTeacherRS();
		courseTeacherRS.setcNo(termCourse.getCourse().getNo());
		courseTeacherRS.settNo(termCourse.getTeacher().getNo());
		courseTeacherRS.setTermNo(termCourse.getTerm().getNo());
		CourseTeacherRS dbCourseTeacherRS = courseTeacherRSMapper.getCourseTeacherRSbyConditions(courseTeacherRS);
		if(dbCourseTeacherRS==null){
			courseTeacherRSMapper.addCourseTeacherRS(courseTeacherRS);
			dbCourseTeacherRS = courseTeacherRS;
		}
		termCourse.setCourseTeacherRSId(dbCourseTeacherRS.getId());
		termCourseMapper.addTermCourse(termCourse);
		return termCourse;
	}
	@Override
	public void deleteTermCourse(TermCourse termCourse) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateTermCourse(TermCourse termCourse) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<TermCourse> getTermCourses() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TermCourse getTermCourseById(int xqid) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<TermCourse> getTermCoursesByTermNo(String termNo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public TermCourse findTermCourseByConditions(TermCourse termCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
