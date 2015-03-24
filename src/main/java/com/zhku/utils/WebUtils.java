package com.zhku.utils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;

import com.zhku.web.Constants.Error;

public class WebUtils {
	
	public static <T> T request2Bean(HttpServletRequest request ,Class<T> beanClass){
		T bean =null;
		try {
			bean =beanClass.newInstance();
			
			@SuppressWarnings("rawtypes")
			Enumeration e =request.getParameterNames();
			
			while(e.hasMoreElements()){
				String name =(String) e.nextElement();
				
				String value =request.getParameter(name);
				
			
				BeanUtils.setProperty(bean, name, value);
				
			}
			
			
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
		return bean;
	}
	public static void coypBean(Object src ,Object dest){
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	public static Map<String,Object> webJsonResult(Object result){
		Map<String,Object> retval = new HashMap<String,Object>();
		retval.put("state", 1);
		retval.put("result", result);
		return retval;
	}
	public static Map<String,Object> webJsonError(String errorMessage){
		Map<String,Object> retval = new HashMap<String,Object>();
		retval.put("state", 0);
		HashMap<String, Object> messageMap = new HashMap<String,Object>();
		messageMap.put("error_description", errorMessage);
		retval.put("error", messageMap);
		return retval;
	}
	public static Map<String,Object> webJsonError(Error error){
		Map<String,Object> retval = new HashMap<String,Object>();
		retval.put("state", 0);
		retval.put("error", error.toMap());
		return retval;
	}


}
