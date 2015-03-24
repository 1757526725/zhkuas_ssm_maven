package com.zhku.service.network.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;
import zhku.jackcan.webCrawler.impl.BinaryData;

import com.alibaba.fastjson.JSON;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.service.network.IWebLogin;
import com.zhku.utils.ServiceUtils;
import com.zhku.utils.WebConfigUtils;
import com.zhku.web.Constants;

public class WebLogin implements IWebLogin {

	public final static int LOGIN_STATE_OK = 1;
	public final static int LOGIN_STATE_ERROR = -1;
	public final static int LOGIN_STATE_WRONGSNORPAS = -2;
	public final static int LOGIN_STATE_WRONGVCODE = -3;
	private int state;
	private String loginUrl;
	private String vCodeUrl;
	private String cookies;
	private FetchUrl fetchUrl;
	private int maxReFetchCount;
	private Map<String, String> formMap = new HashMap<String,String>();
	private static Logger logger = Logger.getLogger(WebLogin.class.getName());

	public WebLogin(String cookies){
		this();
		this.setCookies(cookies);
	}
	public WebLogin() {
		String host ="http://"+ WebConfigUtils.getValue("school_system_host");
		loginUrl = host + "_data/index_LOGIN.aspx";
		setvCodeUrl(host + "sys/ValidateCode.aspx");
		setFetchUrl(FetchUrlFactory.getFetchurl());
		/** 对fetchUrl 设置一些头信息 **/
		fetchUrl.setHeader("Referer", loginUrl);
		maxReFetchCount = Constants.MAX_FETCHURL_COUNT;
	}
	
	@Override
	public String login(String username, String password, String vCode)
			throws LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException {
		formMap = new HashMap<String,String>();
		//请求登录页面获取隐藏表单
		try {
			String html = FetchUrlFactory.getFetchurl().get(loginUrl, Constants.MAX_FETCHURL_COUNT);
			formMap = HTMLUtil.getFormMap_Kingo(html, 0);
		} catch (FetchTimeoutException e) {
			//			e.printStackTrace();
			throw new ConnectionException();
		}
		return login(formMap , username, password, vCode);
	}
	@Override
	public String login(Map<String, String> formMap, String username, String password, String vCode) throws LoginOutTimeException, ConnectionException,
			SnoOrPasswordIncorrectException, ValidateCodeIncorrectException {
		Map<String, String> postParams = (formMap==null?new HashMap<String,String>():formMap);
		postParams.put("Sel_Type", "STU");
		String schoolcode =WebConfigUtils.getValue("kingoCode");
		String chkpwdValue = ServiceUtils.toMD5(username+ServiceUtils.toMD5(password).substring(0,30).toUpperCase()+schoolcode).substring(0,30).toUpperCase();
		String chkyzmValue = ServiceUtils.toMD5(ServiceUtils.toMD5(vCode.toUpperCase()).substring(0,30).toUpperCase()+schoolcode ).substring(0,30).toUpperCase();
		postParams.put(Constants.HTML_ELEMENT_NAME.LOGIN_FORM_NAME_HIDDEN_USERNAME_PASSWORD.getValue(), chkpwdValue);
		postParams.put(Constants.HTML_ELEMENT_NAME.LOGIN_FORM_NAME_HIDDEN_VAILDATECODE.getValue(), chkyzmValue);
		postParams.put(Constants.HTML_ELEMENT_NAME.LOGIN_FORM_NAME_USERNAME.getValue(), username);
		postParams.put(Constants.HTML_ELEMENT_NAME.LOGIN_FORM_NAME_PASSWORD.getValue(), password);
		postParams.put(Constants.HTML_ELEMENT_NAME.LOGIN_FORM_NAME_VAILDATECODE.getValue(), vCode);
//		postParams.put("sbtState", "");
//		postParams.put("__VIEWSTATE", "dDwtMTMwOTYyOTQ5Mjt0PDtsPGk8MD47aTwxPjtpPDI+Oz47bDx0PHA8bDxUZXh0Oz47bDzku7LmgbrlhpzkuJrlt6XnqIvlrabpmaI7Pj47Oz47dDxwPGw8VGV4dDs+O2w8XDxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0Ilw+Clw8IS0tCmZ1bmN0aW9uIENoa1ZhbHVlKCl7CiB2YXIgdlU9JCgnVUlEJykuaW5uZXJIVE1MXDsKIHZVPXZVLnN1YnN0cmluZygwLDEpK3ZVLnN1YnN0cmluZygyLDMpXDsKIHZhciB2Y0ZsYWcgPSAiWUVTIlw7IGlmICgkKCd0eHRfYXNtY2RlZnNkZHNkJykudmFsdWU9PScnKXsKIGFsZXJ0KCfpobvlvZXlhaUnK3ZVKyfvvIEnKVw7JCgndHh0X2FzbWNkZWZzZGRzZCcpLmZvY3VzKClcO3JldHVybiBmYWxzZVw7Cn0KIGVsc2UgaWYgKCQoJ3R4dF9wZXdlcndlZHNkZnNkZmYnKS52YWx1ZT09JycpewogYWxlcnQoJ+mhu+W9leWFpeWvhuegge+8gScpXDskKCd0eHRfcGV3ZXJ3ZWRzZGZzZGZmJykuZm9jdXMoKVw7cmV0dXJuIGZhbHNlXDsKfQogZWxzZSBpZiAoJCgndHh0X3NkZXJ0ZmdzYWRzY3hjYWRzYWRzJykudmFsdWU9PScnICYmIHZjRmxhZyA9PSAiWUVTIil7CiBhbGVydCgn6aG75b2V5YWl6aqM6K+B56CB77yBJylcOyQoJ3R4dF9zZGVydGZnc2Fkc2N4Y2Fkc2FkcycpLmZvY3VzKClcO3JldHVybiBmYWxzZVw7Cn0KIGVsc2UgeyAkKCdkaXZMb2dOb3RlJykuaW5uZXJIVE1MPSdcPGZvbnQgY29sb3I9InJlZCJcPuato+WcqOmAmui/h+i6q+S7vemqjOivgS4uLuivt+eojeWAmSFcPC9mb250XD4nXDsKIHJldHVybiB0cnVlXDt9Cn0KZnVuY3Rpb24gU2VsVHlwZShvYmopewogdmFyIHM9b2JqLm9wdGlvbnNbb2JqLnNlbGVjdGVkSW5kZXhdLmdldEF0dHJpYnV0ZSgndXNySUQnKVw7CiB2YXIgdz1vYmoub3B0aW9uc1tvYmouc2VsZWN0ZWRJbmRleF0uZ2V0QXR0cmlidXRlKCdQd2RJRCcpXDsKICQoJ1VJRCcpLmlubmVySFRNTD1zXDsKIHNlbFR5ZU5hbWUoKVw7CiBpZihvYmoudmFsdWU9PSJTVFUiKSB7CiAgIGRvY3VtZW50LmFsbC5idG5HZXRTdHVQd2Quc3R5bGUuZGlzcGxheT0nJ1w7CiAgIGRvY3VtZW50LmFsbC5idG5SZXNldC5zdHlsZS5kaXNwbGF5PSdub25lJ1w7CiAgfQogZWxzZSB7CiAgICBkb2N1bWVudC5hbGwuYnRuUmVzZXQuc3R5bGUuZGlzcGxheT0nJ1w7CiAgICBkb2N1bWVudC5hbGwuYnRuR2V0U3R1UHdkLnN0eWxlLmRpc3BsYXk9J25vbmUnXDsKICB9fQpmdW5jdGlvbiBvcGVuV2luTG9nKHRoZVVSTCx3LGgpewp2YXIgVGZvcm0scmV0U3RyXDsKZXZhbCgiVGZvcm09J3dpZHRoPSIrdysiLGhlaWdodD0iK2grIixzY3JvbGxiYXJzPW5vLHJlc2l6YWJsZT1ubyciKVw7CnBvcD13aW5kb3cub3Blbih0aGVVUkwsJ3dpbktQVCcsVGZvcm0pXDsgLy9wb3AubW92ZVRvKDAsNzUpXDsKZXZhbCgiVGZvcm09J2RpYWxvZ1dpZHRoOiIrdysicHhcO2RpYWxvZ0hlaWdodDoiK2grInB4XDtzdGF0dXM6bm9cO3Njcm9sbGJhcnM9bm9cO2hlbHA6bm8nIilcOwpwb3AubW92ZVRvKChzY3JlZW4ud2lkdGgtdykvMiwoc2NyZWVuLmhlaWdodC1oKS8yKVw7aWYodHlwZW9mKHJldFN0cikhPSd1bmRlZmluZWQnKSBhbGVydChyZXRTdHIpXDsKfQpmdW5jdGlvbiBzaG93TGF5KGRpdklkKXsKdmFyIG9iakRpdiA9IGV2YWwoZGl2SWQpXDsKaWYgKG9iakRpdi5zdHlsZS5kaXNwbGF5PT0ibm9uZSIpCntvYmpEaXYuc3R5bGUuZGlzcGxheT0iIlw7fQplbHNle29iakRpdi5zdHlsZS5kaXNwbGF5PSJub25lIlw7fQp9CmZ1bmN0aW9uIHNlbFR5ZU5hbWUoKXsKICAkKCd0eXBlTmFtZScpLnZhbHVlPSROKCdTZWxfVHlwZScpWzBdLm9wdGlvbnNbJE4oJ1NlbF9UeXBlJylbMF0uc2VsZWN0ZWRJbmRleF0udGV4dFw7Cn0Kd2luZG93Lm9ubG9hZD1mdW5jdGlvbigpewoJdmFyIHNQQz1NU0lFP3dpbmRvdy5uYXZpZ2F0b3IudXNlckFnZW50K3dpbmRvdy5uYXZpZ2F0b3IuY3B1Q2xhc3Mrd2luZG93Lm5hdmlnYXRvci5hcHBNaW5vclZlcnNpb24rJyBTTjpOVUxMJzp3aW5kb3cubmF2aWdhdG9yLnVzZXJBZ2VudCt3aW5kb3cubmF2aWdhdG9yLm9zY3B1K3dpbmRvdy5uYXZpZ2F0b3IuYXBwVmVyc2lvbisnIFNOOk5VTEwnXDsKdHJ5eyQoJ3BjSW5mbycpLnZhbHVlPXNQQ1w7fWNhdGNoKGVycil7fQp0cnl7JCgndHh0X2FzbWNkZWZzZGRzZCcpLmZvY3VzKClcO31jYXRjaChlcnIpe30KdHJ5eyQoJ3R5cGVOYW1lJykudmFsdWU9JE4oJ1NlbF9UeXBlJylbMF0ub3B0aW9uc1skTignU2VsX1R5cGUnKVswXS5zZWxlY3RlZEluZGV4XS50ZXh0XDt9Y2F0Y2goZXJyKXt9Cn0KZnVuY3Rpb24gb3BlbldpbkRpYWxvZyh1cmwsc2NyLHcsaCkKewp2YXIgVGZvcm1cOwpldmFsKCJUZm9ybT0nZGlhbG9nV2lkdGg6Iit3KyJweFw7ZGlhbG9nSGVpZ2h0OiIraCsicHhcO3N0YXR1czoiK3NjcisiXDtzY3JvbGxiYXJzPW5vXDtoZWxwOm5vJyIpXDsKd2luZG93LnNob3dNb2RhbERpYWxvZyh1cmwsMSxUZm9ybSlcOwp9CmZ1bmN0aW9uIG9wZW5XaW4odGhlVVJMKXsKdmFyIFRmb3JtLHcsaFw7CnRyeXsKCXc9d2luZG93LnNjcmVlbi53aWR0aC0xMFw7Cn1jYXRjaChlKXt9CnRyeXsKaD13aW5kb3cuc2NyZWVuLmhlaWdodC0zMFw7Cn1jYXRjaChlKXt9CnRyeXtldmFsKCJUZm9ybT0nd2lkdGg9Iit3KyIsaGVpZ2h0PSIraCsiLHNjcm9sbGJhcnM9bm8sc3RhdHVzPW5vLHJlc2l6YWJsZT15ZXMnIilcOwpwb3A9cGFyZW50LndpbmRvdy5vcGVuKHRoZVVSTCwnJyxUZm9ybSlcOwpwb3AubW92ZVRvKDAsMClcOwpwYXJlbnQub3BlbmVyPW51bGxcOwpwYXJlbnQuY2xvc2UoKVw7fWNhdGNoKGUpe30KfQpmdW5jdGlvbiBjaGFuZ2VWYWxpZGF0ZUNvZGUoT2JqKXsKdmFyIGR0ID0gbmV3IERhdGUoKVw7Ck9iai5zcmM9Ii4uL3N5cy9WYWxpZGF0ZUNvZGUuYXNweD90PSIrZHQuZ2V0TWlsbGlzZWNvbmRzKClcOwp9CmZ1bmN0aW9uIGNoa3B3ZChvYmopIHsgIGlmKG9iai52YWx1ZSE9JycpICB7ICAgIHZhciBzPW1kNShkb2N1bWVudC5hbGwudHh0X2FzbWNkZWZzZGRzZC52YWx1ZSttZDUob2JqLnZhbHVlKS5zdWJzdHJpbmcoMCwzMCkudG9VcHBlckNhc2UoKSsnMTEzNDcnKS5zdWJzdHJpbmcoMCwzMCkudG9VcHBlckNhc2UoKVw7ICAgZG9jdW1lbnQuYWxsLmRzZHNkc2RzZHhjeGRmZ2ZnLnZhbHVlPXNcO30gZWxzZSB7IGRvY3VtZW50LmFsbC5kc2RzZHNkc2R4Y3hkZmdmZy52YWx1ZT1vYmoudmFsdWVcO30gfSAgZnVuY3Rpb24gY2hreXptKG9iaikgeyAgaWYob2JqLnZhbHVlIT0nJykgeyAgIHZhciBzPW1kNShtZDUob2JqLnZhbHVlLnRvVXBwZXJDYXNlKCkpLnN1YnN0cmluZygwLDMwKS50b1VwcGVyQ2FzZSgpKycxMTM0NycpLnN1YnN0cmluZygwLDMwKS50b1VwcGVyQ2FzZSgpXDsgICBkb2N1bWVudC5hbGwuZmdmZ2dmZGd0eXV1eXl1dWNramcudmFsdWU9c1w7fSBlbHNlIHsgICAgZG9jdW1lbnQuYWxsLmZnZmdnZmRndHl1dXl5dXVja2pnLnZhbHVlPW9iai52YWx1ZS50b1VwcGVyQ2FzZSgpXDt9fS8vLS1cPgpcPC9zY3JpcHRcPjs+Pjs7Pjt0PDtsPGk8MT47PjtsPHQ8O2w8aTwwPjs+O2w8dDxwPGw8VGV4dDs+O2w8XDxvcHRpb24gdmFsdWU9J1NUVScgdXNySUQ9J+WtpuOAgOWPtydcPuWtpueUn1w8L29wdGlvblw+Clw8b3B0aW9uIHZhbHVlPSdURUEnIHVzcklEPSflt6XjgIDlj7cnXD7mlZnluIjmlZnovoXkurrlkZhcPC9vcHRpb25cPgpcPG9wdGlvbiB2YWx1ZT0nU1lTJyB1c3JJRD0n5biQ44CA5Y+3J1w+566h55CG5Lq65ZGYXDwvb3B0aW9uXD4KXDxvcHRpb24gdmFsdWU9J0FETScgdXNySUQ9J+W4kOOAgOWPtydcPumXqOaIt+e7tOaKpOWRmFw8L29wdGlvblw+Cjs+Pjs7Pjs+Pjs+Pjs+Pjs+EywFteMb7DqCXJXTvA4lqtdkuio=");
		fetchUrl.setAllowRedirect(false);
		fetchUrl.setPostData(postParams);
		System.out.println(JSON.toJSONString(postParams));
		String html = null;
		int count = 0;
		while (html == null || fetchUrl.getHttpCode() != 200) {
			try {
				html = fetchUrl.post(getLoginUrl());
			} catch (Exception e) {
				logger.warning("登录请求超时,即将重试");
			}
			if (count > Constants.MAX_FETCHURL_COUNT) {
				throw new LoginOutTimeException();
			}
			count++;
		}
		if (html != null) {
			// System.out.println(responseContent);
			switch (getLoginResult(html)) {
			case LOGIN_STATE_ERROR:
				throw new ConnectionException("教务网系统错误,连接失败...");
			case LOGIN_STATE_WRONGSNORPAS:
				throw new SnoOrPasswordIncorrectException("学号或者密码不正确..");
			case LOGIN_STATE_WRONGVCODE:
				throw new ValidateCodeIncorrectException("验证码不正确..");
			default:
				break;
			}
		}
		return fetchUrl.getResponseCookies()==null?this.getCookies():fetchUrl.getResponseCookies();
	}
	private int getLoginResult(String html) {

		Document doc = null;
		String message = null;
		// System.out.println(html);

		if (html != null && !"".equals(html)) {
			doc = Jsoup.parse(html);
			Elements eles = doc.select("span#divLogNote");
			for (Element e : eles) {
				message = e.text();
				if ((message != null && !message.trim().equals(""))
						&& message.equals("正在加载权限数据...")) {
					this.setState(LOGIN_STATE_OK);
					return LOGIN_STATE_OK;
				}
				if ((message != null && !message.trim().equals(""))
						&& message.equals("帐号或密码不正确！请重新输入。")) {
					this.setState(LOGIN_STATE_WRONGSNORPAS);
					return LOGIN_STATE_WRONGSNORPAS;
				}

				if ((message != null && !message.trim().equals(""))
						&& message.equals("验证码错误！ 登录失败！")) {
					setState(LOGIN_STATE_WRONGVCODE);
					return LOGIN_STATE_WRONGVCODE;
				}
			}

		}
		return LOGIN_STATE_ERROR;
	}

	@Override
	public byte[] getValidateCodeImg() throws FetchTimeoutException{
		//fetchUrl.reset();
		BinaryData image = fetchUrl.getResource(getvCodeUrl(),"vCoed.jpg",Constants.MAX_FETCHURL_COUNT);
		this.cookies=fetchUrl.getResponseCookies()==null?this.cookies:fetchUrl.getResponseCookies();
		return image.getData();
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	@Override
	public String getCookies() {
		return cookies;
	}
	public void setCookies(String cookies) {
		this.fetchUrl.setCookies(cookies);
		this.cookies = cookies;
	}

	public FetchUrl getFetchUrl() {
		return fetchUrl;
	}

	public void setFetchUrl(FetchUrl fetchUrl) {
		this.fetchUrl = fetchUrl;
	}

	public int getMaxReFetchCount() {
		return maxReFetchCount;
	}

	public void setMaxReFetchCount(int maxReFetchCount) {
		this.maxReFetchCount = maxReFetchCount;
	}

	public String getvCodeUrl() {
		return vCodeUrl;
	}

	public void setvCodeUrl(String vCodeUrl) {
		this.vCodeUrl = vCodeUrl;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Map<String, String> getFormMap() {
		return formMap;
	}

	public void setFormMap(Map<String, String> formMap) {
		this.formMap = formMap;
	}



}
