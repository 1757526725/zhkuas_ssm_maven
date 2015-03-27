package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Major;

public interface MajorMapper {
	public void addMajor(Major major);
	public void deleteMajor(Major major);
	public void updateMajor(Major major);
	public Major getMajorById(int id);
	public Major getMajorByNo(String no);
	public Major getMajorByName(String Name);
	public List<Major> getMajors();
}
