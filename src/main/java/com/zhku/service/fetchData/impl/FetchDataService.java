package com.zhku.service.fetchData.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.zhku.bean.BaseClass;
import com.zhku.bean.Course;
import com.zhku.bean.CourseListPage;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.Teacher;
import com.zhku.bean.Term;
import com.zhku.bean.TermCourse;
import com.zhku.service.fetchData.IFetchDataService;
@Service("fetchDataService")
public class FetchDataService implements IFetchDataService{

	@Override
	public List<BaseClass> fetchClassList(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Teacher> fetchTeacherList(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Course> fetchCourseList(Term term) {
		//先获取Course 基本信息 列表
		//再填充完整信息
		return null;
	}

	@Override
	public List<TermCourse> fetchTermCourseList(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicCourse> fetchPublicCourseList(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PublicCourseOption> fetchPublicCourseOption(Term term) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TermCourse> fetchCourseHad() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CourseListPage fetchPublicCourseListPage() {
		// TODO Auto-generated method stub
		return null;
	}

}
