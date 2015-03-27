package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.MyCourseScheme;
import com.zhku.dao.MyCourseSchemeMapper;
import com.zhku.service.db.IMyCourseSchemeService;

/**
 * @author JackCan
 *
 */
@Service("myCourseSchemeService")
public class MyCourseSchemeService  implements IMyCourseSchemeService{

	@Autowired
	private MyCourseSchemeMapper myCourseSchemeMapper;
	@Override
	public void addMyCourseScheme(MyCourseScheme myCourseScheme) {
		myCourseSchemeMapper.addMyCourseScheme(myCourseScheme);
	}

	@Override
	public void deleteMyCourseScheme(MyCourseScheme myCourseScheme) {
		myCourseSchemeMapper.deleteMyCourseScheme(myCourseScheme);
	}

	@Override
	public void updateMyCourseScheme(MyCourseScheme myCourseScheme) {
		myCourseSchemeMapper.updateMyCourseScheme(myCourseScheme);
	}

	@Override
	public List<MyCourseScheme> getMyCourseSchemesByTermNoAndUid(String termNo, int uid) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("termNo", termNo);
		params.put("uid", uid+"");
		return myCourseSchemeMapper.getMyCourseSchemesByTermNoAndUid(params );
	}

	@Override
	public MyCourseScheme getMyCourseSchemeById(int id) {
		return myCourseSchemeMapper.getMyCourseSchemeById(id);
	}

	@Override
	public List<MyCourseScheme> getMyCourseSchemesByUid(int uid) {
		return myCourseSchemeMapper.getMyCourseSchemesByUid(uid);
	}

	@Override
	public MyCourseScheme findMyCourseScheme(MyCourseScheme myCourseScheme) {
		return myCourseSchemeMapper.findMyCourseScheme(myCourseScheme);
	}

	@Override
	public void updateOrdeNumOfMyCourseScheme(MyCourseScheme myCourseScheme, int orderNum) {
		Map<String,String> params = new HashMap<String,String>();
		params.put("id",myCourseScheme.getId()+"");
		params.put("orderNum", orderNum+"");
		myCourseSchemeMapper.updateOrdeNumOfMyCourseScheme(params );
	}

	@Override
	public void deleteSchemesByUid(int uid) {
		myCourseSchemeMapper.deleteSchemesByUid(uid);
	}

	public MyCourseSchemeMapper getMyCourseSchemeMapper() {
		return myCourseSchemeMapper;
	}

	public void setMyCourseSchemeMapper(MyCourseSchemeMapper myCourseSchemeMapper) {
		this.myCourseSchemeMapper = myCourseSchemeMapper;
	}


}
