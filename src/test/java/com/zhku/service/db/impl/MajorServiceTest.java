package com.zhku.service.db.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Major;
import com.zhku.service.db.IMajorService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class MajorServiceTest {
	@Autowired
	private IMajorService majorService; 
	@Test
	public void testAddMajor() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteMajor() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateMajor() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMajorById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMajorByNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMajorByName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMajors() {
		List<Major> majors= majorService.getMajors();
		System.out.println(JSON.toJSONString(majors));
	}

	public IMajorService getMajorService() {
		return majorService;
	}

	public void setMajorService(IMajorService majorService) {
		this.majorService = majorService;
	}

}
