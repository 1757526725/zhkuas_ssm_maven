package com.zhku.service.network.impl;

import java.util.List;

import zhku.jackcan.webCrawler.NameValuePair;

import com.zhku.bean.CourseListPage;
import com.zhku.bean.MyPublicCourse;
import com.zhku.bean.PublicCourseOption;
import com.zhku.bean.Student;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.service.network.INetworkService;
import com.zhku.web.Constants;
/**
 * 教务网请求的服务
 * @author JackCan
 *
 */
public class NetworkServiceImpl implements INetworkService{

	@Override
	public Student getStudentInfo(String cookies) {
		
		
		return null;
	}

	@Override
	public CourseListPage getCourseListPage(String cookies, String majorNo) throws LoginOutTimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicCourseOption getCourseOption(String cookies, String value, String skbjval) throws LoginOutTimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String submitCourse(String cookies, List<NameValuePair> formParams) throws LoginOutTimeException {
		
		return null;
	}

	@Override
	public String getCourseSelected(String cookies) throws LoginOutTimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String dropCourse(String cookies, List<NameValuePair> formParams) throws LoginOutTimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MyPublicCourse> getMyPublicCourses(String cookie, int uid, List<String> termNos) throws LoginOutTimeException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
