package com.zhku.service.db.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Teacher;
import com.zhku.service.db.ITeacherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TeacherServiceTest {
	@Autowired
	private ITeacherService teacherService;
	@Test
	public void testAddTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeacherById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeachers() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeacherByTno() {
//		0000358
		Teacher teacher = teacherService.getTeacherByTno("0000358");
		System.out.println(JSON.toJSONString(teacher));
	}

	@Test
	public void testUpdateTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTeacherByTnameNo() {
		fail("Not yet implemented");
	}

	public ITeacherService getTeacherService() {
		return teacherService;
	}

	public void setTeacherService(ITeacherService teacherService) {
		this.teacherService = teacherService;
	}

}
