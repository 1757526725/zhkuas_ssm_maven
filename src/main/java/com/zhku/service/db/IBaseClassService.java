package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.BaseClass;
import com.zhku.exception.ObjectExistsException;
public interface IBaseClassService {
	public void addBaseClass(BaseClass baseClass) throws ObjectExistsException;
	public void deleteBaseClass(BaseClass baseClass);
	public void updateBaseClass(BaseClass baseClass);
	public BaseClass getBaseClassById(int clid);
	public List<BaseClass> getBaseClasses();
	public List<BaseClass> getBaseClasses(int pageNum,int pageSize,boolean needCountTotal);
	public BaseClass getBaseClassByClassNo(String classNo);
	public List<BaseClass> getBaseClassesByNameArray(String[] classNames);
	public BaseClass getBaseClassWithCourseListByIdAndTermNo(int id,String termNo);
	public BaseClass getBaseClassWithCourseListByClassNoAndTermNo(String classNo,String termNo);
	public List<BaseClass> getBaseClassesWithMajorAndCampus();
	public List<BaseClass> getBaseClassesWithMajorAndCampus(int pageNum,int pageSize,boolean needCountTotal);
	public BaseClass getBaseClassByClassName(String className);
	public List<BaseClass> getBaseClassesByClassName(String keyWord);
	public List<BaseClass> getBaseClassesByMajorNo(String majorNo);
	public List<BaseClass> getBaseClassesByGradeAndAcademy(List<String> grade, String acadmeyNo);
}
