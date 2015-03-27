package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.PublicCourseProfiles;
import com.zhku.dao.PublicCourseProfilesMapper;
import com.zhku.service.db.IPublicCourseProfilesService;
@Service("publicCourseProfilesService")
public class PublicCourseProfilesService implements IPublicCourseProfilesService {
	@Autowired
	private PublicCourseProfilesMapper publicCourseProfilesMapper;
	@Override
	public void addPublicCourseProfiles(PublicCourseProfiles publicCourseProfiles) {
		publicCourseProfilesMapper.addPublicCourseProfiles(publicCourseProfiles);
	}

	@Override
	public void deletePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles) {
		publicCourseProfilesMapper.deletePublicCourseProfiles(publicCourseProfiles);
	}

	@Override
	public void updatePublicCourseProfiles(PublicCourseProfiles publicCourseProfiles) {
		publicCourseProfilesMapper.updatePublicCourseProfiles(publicCourseProfiles);
	}

	@Override
	public PublicCourseProfiles getPublicCourseProfilesByCno(String cNo) {
		return publicCourseProfilesMapper.getPublicCourseProfilesByCno(cNo);
	}

	public PublicCourseProfilesMapper getPublicCourseProfilesMapper() {
		return publicCourseProfilesMapper;
	}

	public void setPublicCourseProfilesMapper(PublicCourseProfilesMapper publicCourseProfilesMapper) {
		this.publicCourseProfilesMapper = publicCourseProfilesMapper;
	}


}
