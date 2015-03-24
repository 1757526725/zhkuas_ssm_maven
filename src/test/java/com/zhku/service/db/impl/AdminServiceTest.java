package com.zhku.service.db.impl;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Admin;
import com.zhku.service.db.IAdminService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AdminServiceTest {
	@Autowired
	private IAdminService adminService;
	@Test
	public void testAddAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAdmin() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAdmin() {
		Admin admin=new Admin();
		admin.setUsername("JackCan");
		admin.setPassword("522415181");
		admin=adminService.findAdmin(admin);
		System.out.println(JSON.toJSONString(admin));
	}

	public IAdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(IAdminService adminService) {
		this.adminService = adminService;
	}

}
