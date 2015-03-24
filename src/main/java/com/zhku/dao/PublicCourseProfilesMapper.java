package com.zhku.dao;

import com.zhku.bean.PublicCourseProfiles;

public interface PublicCourseProfilesMapper {
	public void addPublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public void deletePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public void updatePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public PublicCourseProfiles getPublicCourseProfilesByCno(String cNo);
}
