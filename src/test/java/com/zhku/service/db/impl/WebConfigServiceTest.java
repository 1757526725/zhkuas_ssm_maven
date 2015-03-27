package com.zhku.service.db.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.zhku.bean.WebConfig;
import com.zhku.service.db.IWebConfigService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring.xml","classpath:spring-mybatis.xml"})
public class WebConfigServiceTest {
	@Autowired
	private IWebConfigService webConfigService;
	@Test
	public void testUpdateWebConfig() {
	
	}

	@Test
	public void testGetWebConfig() {
		WebConfig wc=webConfigService.getWebConfig();
		System.out.println(JSON.toJSON(wc));
	}
	

	public IWebConfigService getWebConfigService() {
		return webConfigService;
	}

	public void setWebConfigService(IWebConfigService webConfigService) {
		this.webConfigService = webConfigService;
	}

}
