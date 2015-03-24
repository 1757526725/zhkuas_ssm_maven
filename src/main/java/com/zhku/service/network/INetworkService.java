package com.zhku.service.network;

import java.util.List;

import zhku.jackcan.webCrawler.NameValuePair;

import com.zhku.bean.CourseListPage;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.Student;
import com.zhku.exception.LoginOutTimeException;

public interface INetworkService {
	public Student getStudentInfo(String cookies);
	public CourseListPage getCourseListPage(String cookies,String majorNo) throws LoginOutTimeException; 
	public PublicCourseOption getCourseOption(String cookies,String value,String skbjval) throws LoginOutTimeException;
	public String submitCourse(String cookies, List<NameValuePair> formParams) throws LoginOutTimeException;
	public String getCourseSelected(String cookies) throws LoginOutTimeException;
	public String dropCourse(String cookies, List<NameValuePair> formParams) throws LoginOutTimeException;
	public List<MyPublicCourse> getMyPublicCourses(String cookie,int uid,List<String> termNos) throws LoginOutTimeException;
	
}
