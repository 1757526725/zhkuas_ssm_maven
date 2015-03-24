package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.PublicCourse;
import com.zhku.bean.TermCourse;
import com.zhku.dao.PublicCourseMapper;
import com.zhku.dao.TermCourseMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.IPublicCourseService;
@Service("publicCourseService")
public class PublicCourseService implements IPublicCourseService{

	@Autowired
	private PublicCourseMapper publicCourseMapper;
	@Autowired
	private TermCourseMapper termCourseMapper;
	@Override
	public void addPublicCourse(PublicCourse publicCourse) throws ObjectExistsException {
		PublicCourse dbPublicCourse = null;
		if(publicCourse.getId()!=null){
			dbPublicCourse = publicCourseMapper.getPublicCourseById(publicCourse.getId());
			if(dbPublicCourse!=null){
				throw new ObjectExistsException();
			}
		}
		publicCourseMapper.addPublicCourse(publicCourse);
	}
	@Override
	public void deletePublicCourse(PublicCourse publicCourse) {
		publicCourseMapper.deletePublicCourse(publicCourse);
	}

	@Override
	public List<PublicCourse> getPublicCoursesByTermNoAndCampusId(String termNo, int campusId) {
		Map<String, String> params = new HashMap<String ,String>();
		params.put("termNo", termNo);
		params.put("campusId", campusId+"");
		/*String key ="getPublicCoursesByTermNoAndCampusId"+JSON.toJSONString(params);
		Object obj = CacheUtils.get(key);
		if(obj!=null) return (List<PublicCourse>) obj;
		else{
			List<PublicCourse> data = publicCourseMapper.getPublicCoursesBytermNoAndCompusId(params);
			CacheUtils.add(key, data);
			return data;
		}*/
		return publicCourseMapper.getPublicCoursesBytermNoAndCompusId(params);
	}

	@Override
	public PublicCourse getPublicCourseById(int pcid) {
		return publicCourseMapper.getPublicCourseById(pcid);
	}

	@Override
	public List<PublicCourse> getPublicCoursesByTermNoAndCampusIdAndTypeId(String termNo, int campusId, int typeId) {
		Map<String, String> params = new HashMap<String ,String>();
		params.put("termNo", termNo);
		params.put("campusId", campusId+"");
		params.put("pctId", typeId+"");
//		String key ="getPublicCoursesByTermNoAndCampusIdAndTypeId"+JSON.toJSONString(params);
//		Object obj = CacheUtils.get(key);
//		if(obj!=null) return (List<PublicCourse>) obj;
//		else{
//			List<PublicCourse> data = publicCourseMapper.getPublicCoursesBytermNoAndCompusIdAndTypeId(params);
//			CacheUtils.add(key, data);
//			return data;
//		}
		return publicCourseMapper.getPublicCoursesBytermNoAndCompusIdAndTypeId(params);
	}

	@Override
	public PublicCourse getPublicCourseByTermCourseAndCompusId(TermCourse termCourse, String campusId) {
		if(termCourse.getId()==null)
			termCourse = termCourseMapper.findTermCourseByConditions(termCourse);
		if(termCourse==null) return null;
		Map<String, String> params = new HashMap<String ,String>();
		params.put("termCourseId", termCourse.getId()+"");
		params.put("campusId", campusId+"");
		return publicCourseMapper.getPublicCourseByTermCourseAndCompusId(params);
	}

	
	@Override
	public List<PublicCourse> getPublicCoursesBytermNoAndCompusIdAndCno(String termNo, int campusId, String cNo) {
		Map<String, String> params = new HashMap<String ,String>();
		params.put("termNo", termNo);
		params.put("campusId", campusId+"");
		params.put("cNo", cNo);
		return publicCourseMapper.getPublicCoursesBytermNoAndCompusIdAndCno(params);
	}

	public PublicCourseMapper getPublicCourseMapper() {
		return publicCourseMapper;
	}

	public void setPublicCourseMapper(PublicCourseMapper publicCourseMapper) {
		this.publicCourseMapper = publicCourseMapper;
	}
	public TermCourseMapper getTermCourseMapper() {
		return termCourseMapper;
	}
	public void setTermCourseMapper(TermCourseMapper termCourseMapper) {
		this.termCourseMapper = termCourseMapper;
	}

}
