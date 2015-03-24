package com.zhku.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.BaseClass;
import com.zhku.bean.Major;
import com.zhku.exception.ObjectExistsException;
import com.zhku.module.bo.KeyValue;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.IBaseClassService;
import com.zhku.service.db.IMajorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class DataService {

	@Autowired
	private IBaseClassService baseClassService;
	@Autowired
	private IMajorService majorService;
	@Test
	public void genClassData() throws FetchTimeoutException{
		//获取到年级列表
		FetchHelper fetchHelper = new FetchHelper();
		List<KeyValue> grades = new ArrayList<KeyValue>();
		grades.add(new KeyValue("2014", "2014"));
		List<Major> majors = majorService.getMajors();
		List<BaseClass> classes = new ArrayList<BaseClass>();
		for(Major major : majors){
			List<BaseClass> list = fetchHelper.fetchClassData(major, grades);
			classes.addAll(list);
		}
		System.out.println(JSON.toJSONString(classes));
		
		for(BaseClass baseClass:classes){
			try {
				baseClassService.addBaseClass(baseClass);
			} catch (ObjectExistsException e) {
				System.err.println(JSON.toJSONString(baseClass));
			}
		}
	}
	public IBaseClassService getBaseClassService() {
		return baseClassService;
	}
	public void setBaseClassService(IBaseClassService baseClassService) {
		this.baseClassService = baseClassService;
	}
	public IMajorService getMajorService() {
		return majorService;
	}
	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}
}
