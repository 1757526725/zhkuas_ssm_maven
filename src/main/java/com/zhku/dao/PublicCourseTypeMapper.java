package com.zhku.dao;

import java.util.List;

import com.zhku.bean.PublicCourseType;

public interface PublicCourseTypeMapper {
	public PublicCourseType getPublicCourseTypeById(int id);
	public List<PublicCourseType> getPublicCourseTypes();
}
