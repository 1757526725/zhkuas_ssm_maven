package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.CourseListPage;
import com.zhku.dao.CourseListPageMapper;
import com.zhku.service.db.ICourseListPageService;

@Service("courseListPageService")
public class CourseListPageService implements ICourseListPageService{
	@Autowired
	private CourseListPageMapper courseListPageMapper ;
	@Override
	public void addCourseListPage(CourseListPage courseListPage) {
		courseListPageMapper.addCourseListPage(courseListPage);
	}

	@Override
	public void deleteCourseListPage(CourseListPage courseListPage) {
		courseListPageMapper.deleteCourseListPage(courseListPage);
	}

	@Override
	public void updateCourseListPage(CourseListPage courseListPage) {
		courseListPageMapper.updateCourseListPage(courseListPage);
	}

	@Override
	public CourseListPage getCourseListPageById(int clpid) {
		return courseListPageMapper.getCourseListPageById(clpid);
	}

	@Override
	public CourseListPage getCourseListPageByMajorNo(String majorNo) {
//		String key = "getCourseListPageByMajorNo"+majorNo;
//		Object obj = CacheUtils.get(key);
//		if(obj!=null){
//			return (CourseListPage) obj;
//		}else{
//			CourseListPage data = courseListPageMapper.getCourseListPageByMajorNo(majorNo);
//			CacheUtils.add(key, data);
//			return data;
//		}
		return courseListPageMapper.getCourseListPageByMajorNo(majorNo);
	}

	public CourseListPageMapper getCourseListPageMapper() {
		return courseListPageMapper;
	}

	public void setCourseListPageMapper(CourseListPageMapper courseListPageMapper) {
		this.courseListPageMapper = courseListPageMapper;
	}

	
}
