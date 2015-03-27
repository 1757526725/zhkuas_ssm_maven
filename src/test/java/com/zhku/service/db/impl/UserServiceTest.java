package com.zhku.service.db.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.zhku.bean.User;
import com.zhku.exception.UserExistException;
import com.zhku.service.db.IUserService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class UserServiceTest {

	@Autowired
	private IUserService userService;
	@Test
	public void testRegister() {
		User user =new User();
		user.setAvatorUrl("http://jackcan.sinaapp.com");
		user.setEmail("912536370@qq.com");
		user.setSinaUid("0000000");
		user.setNickName("JackCan");
		
		try {
			userService.register(user);
		} catch (UserExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetUserById() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserBySno() {
		List<User> users=userService.getUserBySno("201111314424");
		System.out.println(JSON.toJSONString(users));
	}

	@Test
	public void testGetUserBySid() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserByEmail() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUsers() {
		List<User> list = userService.getUsers();
		System.out.println(JSON.toJSONString(list));
	}
	@Test
	public void testGetUsersPagition() {
		List<User> list = userService.getUsers(2,10,true);
		Page page = (Page)(list); 
		System.out.println(JSON.toJSONString(list));
		System.out.println(page.getTotal());
	}
	@Test
	public void testFindUserBySinaUid() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAdminUserBySinaUid() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUserMapper() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetUserMapper() {
		fail("Not yet implemented");
	}
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
