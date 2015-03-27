package com.zhku.module.fetchData.bo;

import java.util.List;
import java.util.Map;

import zhku.jackcan.webCrawler.NameValuePair;



public class FetchRequest {
	private String cookies;
	private String referer;
	private Map<String,String> postParamsMap;
	private List<NameValuePair> postData;
	private String requestUrl;
	private int reDoTimes;
	public String getCookies() {
		return cookies;
	}
	public FetchRequest setCookies(String cookies) {
		this.cookies = cookies;
		return this;
	}
	public Map<String,String> getPostParams() {
		return postParamsMap;
	}
	public FetchRequest setPostParamsMap(Map<String,String> postParamsMap) {
		this.postParamsMap = postParamsMap;
		return this;
	}
	public String getRequestUrl() {
		return requestUrl;
	}
	public FetchRequest setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
		return this;
	}
	public int getReDoTimes() {
		return reDoTimes;
	}
	public FetchRequest setReDoTimes(int reDoTimes) {
		this.reDoTimes = reDoTimes;
		return this;
	}
	public String getReferer() {
		if(referer==null) 
			return requestUrl;
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	public List<NameValuePair> getPostData() {
		return postData;
	}
	public void setPostData(List<NameValuePair> postData) {
		this.postData = postData;
	}
}
