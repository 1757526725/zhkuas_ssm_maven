package com.zhku.dao;

import java.util.List;
import java.util.Map;

import com.zhku.bean.MyPublicCourse;

public interface MyPublicCourseMapper {
	public void addMyPublicCouse(MyPublicCourse myPublicCourse);
	public void updateMyPublicCouse(MyPublicCourse myPublicCourse);
	public void deleteMyPublicCouse(MyPublicCourse myPublicCourse);
	public MyPublicCourse getMyPublicCouseById(int mpcid);
	public List<MyPublicCourse> getMyPublicCoursesByUid(int uid);
	public MyPublicCourse getMyPublicCouseByCNameNoAndUid(Map<String,String> params);
}
