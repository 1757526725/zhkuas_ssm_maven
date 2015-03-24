package com.zhku.service.db.impl;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.Term;
import com.zhku.service.db.ITermService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class TermServiceTest {
	@Autowired
	private ITermService termService;
	@Test
	public void testGetTermByNo() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTerms() {
		List<Term> list=termService.getTerms();
		System.out.println(JSON.toJSON(list));
	}

	public ITermService getTermService() {
		return termService;
	}

	public void setTermService(ITermService termService) {
		this.termService = termService;
	}

}
