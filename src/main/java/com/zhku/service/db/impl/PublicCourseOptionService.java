package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.PublicCourseOption;
import com.zhku.dao.PublicCourseOptionMapper;
import com.zhku.service.db.IPublicCourseOptionService;
@Service("publicCourseOptionService")
public class PublicCourseOptionService  implements IPublicCourseOptionService{

	@Autowired
	private PublicCourseOptionMapper publicCourseOptionMapper;
	@Override
	public void addPublicCourseOption(PublicCourseOption publicCourseOption) {
		publicCourseOptionMapper.addPublicCourseOption(publicCourseOption);
	}

	@Override
	public void deletePublicCourseOption(PublicCourseOption publicCourseOption) {
		publicCourseOptionMapper.deletePublicCourseOption(publicCourseOption);
	}

	@Override
	public void updatePublicCourseOption(PublicCourseOption publicCourseOption) {
		publicCourseOptionMapper.updatePublicCourseOption(publicCourseOption);
	}

	@Override
	public PublicCourseOption getPublicCourseOptionById(int pcoid) {
		return publicCourseOptionMapper.getPublicCourseOptionById(pcoid);
	}

	@Override
	public PublicCourseOption findPublicCourseOption(PublicCourseOption publicCourseOption) {
//		String key="findPublicCourseOption"+JSON.toJSONString(publicCourseOption);
//		Object obj = CacheUtils.get(key);
//		if(obj!=null){
//			return (PublicCourseOption) obj;
//		}else{
//			PublicCourseOption data = publicCourseOptionMapper.findPublicCourseOption(publicCourseOption);
//			CacheUtils.add(key, data);
//			return data;
//		}
	return publicCourseOptionMapper.findPublicCourseOption(publicCourseOption);
	}

	public PublicCourseOptionMapper getPublicCourseOptionMapper() {
		return publicCourseOptionMapper;
	}

	public void setPublicCourseOptionMapper(PublicCourseOptionMapper publicCourseOptionMapper) {
		this.publicCourseOptionMapper = publicCourseOptionMapper;
	}


}
