package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.CourseTypeRecord;
import com.zhku.dao.CourseTypeRecordMapper;
import com.zhku.service.db.ICourseTypeRecordService;
@Service("courseTypeRecordService")
public class CourseTypeRecordService implements ICourseTypeRecordService{
	@Autowired 
	private CourseTypeRecordMapper courseTypeRecordMapper;
	@Override
	public CourseTypeRecord getCourseTypeRecordByCName(String cName) {
		return courseTypeRecordMapper.getCourseTypeRecordByCName(cName);
	}
	public CourseTypeRecordMapper getCourseTypeRecordMapper() {
		return courseTypeRecordMapper;
	}
	public void setCourseTypeRecordMapper(CourseTypeRecordMapper courseTypeRecordMapper) {
		this.courseTypeRecordMapper = courseTypeRecordMapper;
	}

	
}
