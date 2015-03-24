package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.PublicCourseType;
import com.zhku.dao.PublicCourseTypeMapper;
import com.zhku.service.db.IPublicCourseTypeService;

@Service("publicCourseTypeService")
public class PublicCourseTypeService implements IPublicCourseTypeService{
	@Autowired
	private PublicCourseTypeMapper publicCourseTypeMapper;
	@Override
	public PublicCourseType getPublicCourseTypeById(int id) {
		return publicCourseTypeMapper.getPublicCourseTypeById(id);
	}
	public PublicCourseTypeMapper getPublicCourseTypeMapper() {
		return publicCourseTypeMapper;
	}
	public void setPublicCourseTypeMapper(PublicCourseTypeMapper publicCourseTypeMapper) {
		this.publicCourseTypeMapper = publicCourseTypeMapper;
	}

}
