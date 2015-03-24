package com.zhku.service.db.impl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Account;
import com.zhku.service.db.IAccountService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class AccountServiceTest {

	@Autowired
	private IAccountService accountService;
	@Test
	public void testAddAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdateAccount() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAccountsByCampusId() {
		List<Account> list = accountService.getAccountsByCampusId(1);
		System.out.println(JSON.toJSONString(list));
	}

	public IAccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(IAccountService accountService) {
		this.accountService = accountService;
	}

}
