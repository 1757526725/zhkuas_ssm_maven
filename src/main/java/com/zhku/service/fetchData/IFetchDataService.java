package com.zhku.service.fetchData;

import java.util.List;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Course;
import com.zhku.bean.CourseListPage;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.Teacher;
import com.zhku.bean.Term;
import com.zhku.bean.TermCourse;

public interface IFetchDataService {
	public List<BaseClass> fetchClassList(Term term);
	public List<Teacher> fetchTeacherList(Term term);
	public List<Course> fetchCourseList(Term term);
	public List<TermCourse> fetchTermCourseList(Term term);
	public List<PublicCourse> fetchPublicCourseList(Term term);
	public List<PublicCourseOption> fetchPublicCourseOption(Term term);
	public List<TermCourse> fetchCourseHad(); 
	public CourseListPage fetchPublicCourseListPage();
}
