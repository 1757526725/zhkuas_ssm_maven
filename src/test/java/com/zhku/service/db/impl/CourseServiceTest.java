package com.zhku.service.db.impl;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Course;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.ICourseService;
import com.zhku.service.db.IOrganizationService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class CourseServiceTest {
	@Autowired
	private IOrganizationService organizationService;
	@Autowired
	private ICourseService courseService;
	@Test
	public void testAddCourse() {
		Course course = new Course();
		course.setNo("222222");
		try {
			courseService.addCourse(course);
		} catch (ObjectExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteCourse() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateCourse() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCourseById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCourses() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCourseByCno() {
		Course ccc = courseService.getCourseByCno("120576");
		System.out.println(JSON.toJSONString(ccc));
	}

	@Test
	public void testGetCourseByCnameNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetCourseWitchDetailByCno() {
		fail("Not yet implemented");
	}

	public IOrganizationService getOrganizationService() {
		return organizationService;
	}

	public void setOrganizationService(IOrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	public ICourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(ICourseService courseService) {
		this.courseService = courseService;
	}

}
