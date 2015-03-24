package com.zhku.module.validatecode;

import java.util.Date;

import org.apache.log4j.Logger;

import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;
import zhku.jackcan.webCrawler.impl.BinaryData;

import com.zhku.module.validatecode.bo.ValidateCode;
import com.zhku.utils.WebConfigUtils;
import com.zhku.web.Constants;
import com.zhku.web.Constants.URL;

public class ValidateCodeFetcher {

	private Logger logger =Logger.getLogger(ValidateCodeFetcher.class.getName());
	public ValidateCode getValidateCode() throws FetchTimeoutException {
		FetchUrl fetchurl = FetchUrlFactory.getFetchurl();
		String url ="http://"+ WebConfigUtils.getValue("school_system_host")+ "sys/ValidateCode.aspx";
		BinaryData image = fetchurl.getResource(url, 2);
		ValidateCode validateCode = new ValidateCode();
		validateCode.setImage(image.getData());
		validateCode.setState(true);
		validateCode.setTime(new Date());
		validateCode.setCookie(fetchurl.getResponseCookies());
		return validateCode;
	}
}
