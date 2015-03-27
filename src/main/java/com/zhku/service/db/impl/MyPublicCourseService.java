package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.MyPublicCourse;
import com.zhku.dao.MyPublicCourseMapper;
import com.zhku.service.db.IMyPublicCourseService;
@Service("myPublicCourseService")
public class MyPublicCourseService  implements IMyPublicCourseService {
	@Autowired 
	private MyPublicCourseMapper myPublicCourseMapper;
	@Override
	public void addMyPublicCouse(MyPublicCourse myPublicCourse) {
		myPublicCourseMapper.addMyPublicCouse(myPublicCourse);
		
	}

	@Override
	public void updateMyPublicCouse(MyPublicCourse myPublicCourse) {
		myPublicCourseMapper.updateMyPublicCouse(myPublicCourse);
	}

	@Override
	public void deleteMyPublicCouse(MyPublicCourse myPublicCourse) {
		myPublicCourseMapper.deleteMyPublicCouse(myPublicCourse);
	}

	@Override
	public MyPublicCourse getMyPublicCouseById(int mpcid) {
		return myPublicCourseMapper.getMyPublicCouseById(mpcid);
	}

	@Override
	public List<MyPublicCourse> getMyPublicCoursesByUid(int uid) {
		return myPublicCourseMapper.getMyPublicCoursesByUid(uid);
	}


	public MyPublicCourseMapper getMyPublicCourseMapper() {
		return myPublicCourseMapper;
	}

	public void setMyPublicCourseMapper(MyPublicCourseMapper myPublicCourseMapper) {
		this.myPublicCourseMapper = myPublicCourseMapper;
	}

	@Override
	public MyPublicCourse getMyPublicCouseByCNameNoAndUid(String cNameNo, Integer uid) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("cNameNo", cNameNo);
		params.put("uid", uid+"");
		return myPublicCourseMapper.getMyPublicCouseByCNameNoAndUid(params);
	}


}
