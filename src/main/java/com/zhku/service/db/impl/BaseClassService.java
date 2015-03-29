package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.BaseClass;
import com.zhku.bean.CampusArea;
import com.zhku.dao.BaseClassMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.IBaseClassService;
@Service("baseClassServcie")
public class BaseClassService implements IBaseClassService {
//	private static Pattern gradeParamChecker = Pattern.compile("\\d([ ]*,[ ]*\\d)*?");
	@Autowired
	private BaseClassMapper baseClassMapper;
	@Override
	public void addBaseClass(BaseClass baseClass) throws ObjectExistsException {
		BaseClass dbclass = baseClassMapper.getBaseClassByClassNo(baseClass.getNo());
		if(dbclass!=null){
			throw new ObjectExistsException();
		}else{
			baseClassMapper.addBaseClass(baseClass);
		}
	}

	@Override
	public void deleteBaseClass(BaseClass baseClass) {
		baseClassMapper.deleteBaseClass(baseClass);
	}

	@Override
	public void updateBaseClass(BaseClass baseClass) {
		baseClassMapper.addBaseClass(baseClass);
	}

	@Override
	public BaseClass getBaseClassById(int clid) {
		return baseClassMapper.getBaseClassById(clid);
	}

	@Override
	public List<BaseClass> getBaseClasses() {
		return baseClassMapper.getBaseClasses();
	}

	@Override
	public BaseClass getBaseClassByClassNo(String classNo) {
		return baseClassMapper.getBaseClassByClassNo(classNo);
	}

	@Override
	public List<BaseClass> getBaseClassesByNameArray(String[] classNames) {
		return baseClassMapper.getBaseClassesByNameArray(classNames);
	}

	@Override
	public BaseClass getBaseClassByClassName(String className) {
		return baseClassMapper.getBaseClassByClassName(className);
	}

	@Override
	public List<BaseClass> getBaseClassesByClassName(String keyWord) {
		return baseClassMapper.getBaseClassesByClassName(keyWord);
	}

	public BaseClassMapper getBaseClassMapper() {
		return baseClassMapper;
	}

	public void setBaseClassMapper(BaseClassMapper baseClassMapper) {
		this.baseClassMapper = baseClassMapper;
	}

	@Override
	public BaseClass getBaseClassWithCourseListByIdAndTermNo(int id, String termNo) {
		Map<String, String> params = new HashMap<String ,String>();
		params.put("id", id+"");
		params.put("termNo", termNo);
		return baseClassMapper.getBaseClassWithCourseListByIdAndTermNo(params);
	}

	@Override
	public BaseClass getBaseClassWithCourseListByClassNoAndTermNo(String classNo, String termNo) {
		Map<String, String> params = new HashMap<String ,String>();
		params.put("no", classNo);
		params.put("termNo", termNo);
		return baseClassMapper.getBaseClassWithCourseListByClassNoAndTermNo(params);
	}

	@Override
	public List<BaseClass> getBaseClassesByMajorNo(String majorNo) {
		return baseClassMapper.getBaseClassesByMajorNo(majorNo);
	}

	@Override
	public List<BaseClass> getBaseClasses(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize,needCountTotal);
		return baseClassMapper.getBaseClasses();
	}

	@Override
	public List<BaseClass> getBaseClassesWithMajorAndCampus() {
		return baseClassMapper.getBaseClassesWithMajorAndCampus();
	}

	@Override
	public List<BaseClass> getBaseClassesWithMajorAndCampus(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize,needCountTotal);
		return baseClassMapper.getBaseClassesWithMajorAndCampus();
	}

	@Override
	public List<BaseClass> getBaseClassesByGradeAndAcademy(List<String> grade, String acadmeyNo) {
		StringBuffer sb = new StringBuffer();
		if(grade==null||grade.size()==0){
			grade  =null;
		}else{
			for(String gd : grade){
				sb.append(gd).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		Map<String,String> params = new HashMap<String,String> ();
		params.put("grades", grade==null?null:sb.toString());
		params.put("acadmeyNo",acadmeyNo );
		return baseClassMapper.getBaseClassesByGradeAndAcademy(params);
	}
	
}
