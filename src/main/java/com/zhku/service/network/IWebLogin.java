package com.zhku.service.network;

import java.util.Map;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;
import com.zhku.exception.ConnectionException;
import com.zhku.exception.LoginOutTimeException;
import com.zhku.exception.SnoOrPasswordIncorrectException;
import com.zhku.exception.ValidateCodeIncorrectException;
/**
 * 
 * @author 嘉灿
 *
 */
public interface IWebLogin {
	/**
	 * 教务网登录接口
	 * @param username
	 * @param password
	 * @param vCode
	 * @return
	 * @throws LoginOutTimeException 
	 * @throws ConnectionException 
	 * @throws SnoOrPasswordIncorrectException 
	 * @throws ValidateCodeIncorrectException 
	 */
	public String login(String username,String password,String vCode) throws LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException;
	public String login(Map<String,String> formMap,String username,String password,String vCode) throws LoginOutTimeException, ConnectionException, SnoOrPasswordIncorrectException, ValidateCodeIncorrectException;
	
	/**
	 * 获取验证码接口
	 * @return
	 * @throws FecturlTimeOutException 
	 * @throws FetchTimeoutException 
	 */
	public byte[] getValidateCodeImg() throws FetchTimeoutException;
	public String getCookies();
	public Map getFormMap();
}
