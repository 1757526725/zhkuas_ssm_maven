package com.zhku.service.db.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Academy;
import com.zhku.service.db.IOrganizationService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AcadmeyServiceTest {
	@Autowired
	private IOrganizationService organizationService;
	@Test
	public void testGetDefaultONo() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddAcadmey() {/*
		Academy acadmey=new Academy();
		acadmey.setAcadmeyName("计算科学学院");
		acadmey.setAcadmeyNo("11");
		organizationService.addAcadmey(acadmey);*/
	}

	@Test
	public void testDeleteAcadmey() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAcadmey() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAcadmeyById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAcadmeyByNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAcadmeyByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAcadmeys() {
		List<Academy> list =organizationService.getAcademys();
		System.out.println(list);
	}
	@Test
	public void testGetOrganizations() {
		List<Academy> list =organizationService.getOrganizations();
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testGetOrganizationsPagition() {
		List<Academy> list =organizationService.getOrganizations();
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testGetAcadmeyMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetAcadmeyMapper() {
		fail("Not yet implemented");
	}

	public IOrganizationService getAcadmeyService() {
		return organizationService;
	}

	public void setAcadmeyService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

}
