package com.zhku.service.db;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.PublicCourseComment;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class IPublicCourseCommentServiceTest {

	@Autowired
	private IPublicCourseCommentService publicCourseCommentService;
	@Test
	public void testAddPublicCourseComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeletePublicCourseComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdatePublicCourseComment() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPublicCourseCommentById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPublicCourseCommentByCno() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPublicCourseCommentsByUid() {
		List<PublicCourseComment> list = publicCourseCommentService.getPublicCourseCommentsByUid(1);
		System.out.println(JSON.toJSONString(list));
	}

	@Test
	public void testGetPublicCourseCommentReplyByUid() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPublicCourseCommentUnreadByUid() {
		fail("Not yet implemented");
	}

	public IPublicCourseCommentService getPublicCourseCommentService() {
		return publicCourseCommentService;
	}

	public void setPublicCourseCommentService(IPublicCourseCommentService publicCourseCommentService) {
		this.publicCourseCommentService = publicCourseCommentService;
	}

}
