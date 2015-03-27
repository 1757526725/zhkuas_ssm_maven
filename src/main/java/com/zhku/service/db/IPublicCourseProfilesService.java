package com.zhku.service.db;

import com.zhku.bean.PublicCourseProfiles;

public interface IPublicCourseProfilesService {
	public void addPublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public void deletePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public void updatePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles);
	public PublicCourseProfiles getPublicCourseProfilesByCno(String cNo);
}
