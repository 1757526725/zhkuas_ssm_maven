package com.zhku.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhku.bean.User;
import com.zhku.service.db.IUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TestMyBatis {
	@Autowired
	private ApplicationContext ac;
	@Autowired
	private IUserService userService;
	
	public ApplicationContext getAc() {
		return ac;
	}
	public void setAc(ApplicationContext ac) {
		this.ac = ac;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	@Test
	public void test(){
		
		User u=userService.getUserById(1);
		System.out.println(u.getNickName());
		
	}
}
