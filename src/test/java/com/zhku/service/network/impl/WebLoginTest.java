package com.zhku.service.network.impl;

import java.util.HashMap;

import org.junit.Test;

import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.utils.ValidateCodeInputHelper;

public class WebLoginTest {

	@Test
	public void testLogin() throws FetchTimeoutException{
		WebLogin webLogin = new WebLogin();
		byte[] img = webLogin.getValidateCodeImg();
		String vCode = ValidateCodeInputHelper.showValidateCode(img);
		System.out.println(webLogin.getCookies());
		try {
			WebLogin webLogin2 = new WebLogin();
			webLogin2.setCookies(webLogin.getCookies());
			String cookies = webLogin2.login(null,"201111314424", "522415181", vCode);
			FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
			fetchUrl.setCookies(cookies);
			System.out.println(cookies);
			String str = fetchUrl.get("http://jw.zhku.edu.cn/jwmis/MAINFRM.aspx");
			
			System.out.println(str);
			System.out.println(fetchUrl.getResponseCookies());
			str = fetchUrl.get("http://jw.zhku.edu.cn/jwmis/JXJH/INFO_KC.aspx?id=110570");
			System.out.println(str);
		} catch (LoginOutTimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SnoOrPasswordIncorrectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidateCodeIncorrectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
