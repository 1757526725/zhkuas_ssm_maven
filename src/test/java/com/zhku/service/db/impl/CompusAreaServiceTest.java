package com.zhku.service.db.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.CampusArea;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class CompusAreaServiceTest {
	@Autowired
	private CampusAreaService campusAreaService;
	
	@Test
	public void getCampusWithBuildingListTest(){
	 CampusArea campus = campusAreaService.getCampusAreaWithBuildingListById(3);
	 System.out.println(JSON.toJSONString(campus));
	}
	@Test
	public void testAddCompusArea() {
		CampusArea campusArea=new CampusArea();
/*		compusArea.setId(1);
		compusArea.setName("海珠校区");*/
/*		compusArea.setId(2);
		compusArea.setName("信息学院");*/
		campusArea.setId(3);
		campusArea.setName("白云校区");
		campusAreaService.addCampusArea(campusArea);
	}

	@Test
	public void testGetCompusAreaById() {
		CampusArea campusArea = campusAreaService.getCampusAreaById(1);
		System.out.println(JSON.toJSONString(campusArea));
	}

	@Test
	public void testGetCompusAreas() {
		List<CampusArea> list = campusAreaService.getCampusAreas();
		System.out.println(JSON.toJSONString(list));

	}

	public CampusAreaService getCompusAreaService() {
		return campusAreaService;
	}

	public void setCompusAreaService(CampusAreaService campusAreaService) {
		this.campusAreaService = campusAreaService;
	}

}
