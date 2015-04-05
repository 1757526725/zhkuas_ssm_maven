package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.PublicCourseType;

public interface IPublicCourseTypeService {
	public PublicCourseType getPublicCourseTypeById(int id);
	public List<PublicCourseType> getPublicCourseTypes();
}
