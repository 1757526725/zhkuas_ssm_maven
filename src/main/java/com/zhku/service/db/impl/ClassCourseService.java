package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.BaseClass;
import com.zhku.bean.ClassCourse;
import com.zhku.bean.TermCourse;
import com.zhku.dao.ClassCourseMapper;
import com.zhku.service.db.IClassCourseService;

@Service("classCourseService")
public class ClassCourseService implements IClassCourseService {

	@Autowired
	private ClassCourseMapper classCourseMapper;
	
	@Override
	public void addClassCourse(ClassCourse classCourse) {
		classCourseMapper.addClassCourse(classCourse);
	}

	@Override
	public void deleteClassCourse(ClassCourse classCourse) {
		classCourseMapper.deleteClassCourse(classCourse);
	}

	@Override
	public void updateClassCourse(ClassCourse classCourse) {
		classCourseMapper.updateClassCourse(classCourse);
	}

	@Override
	public ClassCourse getClassCourseById(int ccid) {
		return classCourseMapper.getClassCourseById(ccid);
	}

	@Override
	public List<ClassCourse> getClassCourses() {
		return classCourseMapper.getClassCourses();
	}

	@Override
	public ClassCourse findClassCourse(BaseClass baseClass, TermCourse termCourse) {
		Map<String,String> params= new HashMap<String,String>();
		params.put("classNo", baseClass.getNo());
		params.put("termCourseId", termCourse.getId()+"");
		return classCourseMapper.findClassCourse(params);
	}

	public ClassCourseMapper getClassCourseMapper() {
		return classCourseMapper;
	}

	public void setClassCourseMapper(ClassCourseMapper classCourseMapper) {
		this.classCourseMapper = classCourseMapper;
	}


}
