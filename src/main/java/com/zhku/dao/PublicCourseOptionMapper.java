package com.zhku.dao;

import com.zhku.bean.PublicCourseOption;

public interface PublicCourseOptionMapper {
	public void addPublicCourseOption(PublicCourseOption publicCourseOption);
	public void deletePublicCourseOption(PublicCourseOption publicCourseOption);
	public void updatePublicCourseOption(PublicCourseOption publicCourseOption);
	public PublicCourseOption getPublicCourseOptionById(int pcoid);
	/**
	 * 
	 * @param publicCourseOption 查找的参数，需要提供 里面的 termNo ,compusId, cNO
	 * @return
	 */
	public PublicCourseOption findPublicCourseOption(PublicCourseOption publicCourseOption);
}
