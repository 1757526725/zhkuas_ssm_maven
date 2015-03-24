package com.zhku.service.db;

import com.zhku.bean.CourseListPage;

public interface ICourseListPageService {
	public void addCourseListPage(CourseListPage courseListPage);
	public void deleteCourseListPage(CourseListPage courseListPage);
	public void updateCourseListPage(CourseListPage courseListPage);
	public CourseListPage getCourseListPageById(int id);
	/**
	 * 
	 * @param MajorNo 1 代表 海珠区公选课 3 代表白云区公选课 其他的代表各专业的代号
	 * @return
	 */
	public CourseListPage getCourseListPageByMajorNo(String majorNo);
}
