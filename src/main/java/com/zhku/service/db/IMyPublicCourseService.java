package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.MyPublicCourse;

public interface IMyPublicCourseService {
	public void addMyPublicCouse(MyPublicCourse myPublicCourse);
	public void updateMyPublicCouse(MyPublicCourse myPublicCourse);
	public void deleteMyPublicCouse(MyPublicCourse myPublicCourse);
	public MyPublicCourse getMyPublicCouseById(int id);
	public List<MyPublicCourse> getMyPublicCoursesByUid(int uid);
	public MyPublicCourse getMyPublicCouseByCNameNoAndUid(String cNameNo,Integer uid);
}
