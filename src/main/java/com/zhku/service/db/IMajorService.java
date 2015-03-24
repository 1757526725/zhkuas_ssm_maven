package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Major;
import com.zhku.exception.ObjectExistsException;

public interface IMajorService {
	public void addMajor(Major major) throws ObjectExistsException;
	public void deleteMajor(Major major);
	public void updateMajor(Major major);
	public Major getMajorById(int id);
	public Major getMajorByNo(String no);
	public Major getMajorByName(String name);
	public List<Major> getMajors();
	public List<Major> getMajors(int pageNum,int pageSize,boolean needCountTotal);
}
