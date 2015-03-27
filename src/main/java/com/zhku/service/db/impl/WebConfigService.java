package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.WebConfig;
import com.zhku.dao.WebConfigMapper;
import com.zhku.service.db.IWebConfigService;
@Service("webConfigService")
public class WebConfigService implements IWebConfigService {
	@Autowired
	private WebConfigMapper webConfigMapper;
	@Override
	public void updateWebConfig(WebConfig webConfig) {
		webConfigMapper.updateWebConfig(webConfig);
	}

	@Override
	public WebConfig getWebConfig() {
		return webConfigMapper.getWebConfig();
	}

	public WebConfigMapper getWebConfigMapper() {
		return webConfigMapper;
	}

	public void setWebConfigMapper(WebConfigMapper webConfigMapper) {
		this.webConfigMapper = webConfigMapper;
	}

}
