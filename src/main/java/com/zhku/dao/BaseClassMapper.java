package com.zhku.dao;

import java.util.List;
import java.util.Map;

import com.zhku.bean.BaseClass;

public interface BaseClassMapper {
	
	public void addBaseClass(BaseClass baseClass);
	public void deleteBaseClass(BaseClass baseClass);
	public void updateBaseClass(BaseClass baseClass);
	public BaseClass getBaseClassById(int clid);
	public List<BaseClass> getBaseClasses();
	public BaseClass getBaseClassByClassNo(String classNo);
	public List<BaseClass> getBaseClassesByNameArray(String[] classNames);
	/**
	 * 
	 * @param params clid ,termNo
	 * @return
	 */
	public BaseClass getBaseClassWithCourseListByIdAndTermNo(Map<String,String> params);
	/**
	 * 
	 * @param params classNo ,termNo
	 * @return
	 */
	public BaseClass getBaseClassWithCourseListByClassNoAndTermNo(Map<String,String> params);
	public BaseClass getBaseClassByClassName(String className);
	public List<BaseClass> getBaseClassesByClassName(String _keyword);
	public List<BaseClass> getBaseClassesByMajorNo(String majorNo);
	public List<BaseClass> getBaseClassesWithMajorAndCampus();
	public List<BaseClass> getBaseClassesByGradeAndAcademy(Map<String,String> params);
}
