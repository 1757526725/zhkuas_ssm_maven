package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.zhku.bean.Major;
import com.zhku.dao.MajorMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.IMajorService;
@Service("majorService")
public class MajorService implements IMajorService {
	@Autowired
	private MajorMapper majorMapper;
	@Override
	public void addMajor(Major major) throws ObjectExistsException {
		Major majorTemp =majorMapper.getMajorByNo(major.getNo());
		if(majorTemp!=null){
			throw new ObjectExistsException("Major:"+JSON.toJSONString(majorTemp));
		}else{
			majorMapper.addMajor(major);
		}
	}

	@Override
	public void deleteMajor(Major major) {
		majorMapper.deleteMajor(major);
	}

	@Override
	public void updateMajor(Major major) {
		majorMapper.updateMajor(major);
	}

	@Override
	public Major getMajorById(int id) {
		return majorMapper.getMajorById(id);
	}

	@Override
	public Major getMajorByNo(String no) {
		return majorMapper.getMajorByNo(no);
	}

	@Override
	public Major getMajorByName(String name) {
		return majorMapper.getMajorByName(name);
	}

	@Override
	public List<Major> getMajors() {
		return majorMapper.getMajors();
	}

	public MajorMapper getMajorMapper() {
		return majorMapper;
	}

	public void setMajorMapper(MajorMapper majorMapper) {
		this.majorMapper = majorMapper;
	}

	@Override
	public List<Major> getMajors(int pageNum, int pageSize,boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize,needCountTotal);
		return majorMapper.getMajors();

	}

}
