package com.zhku.module.fetchData;

import java.util.List;

import zhku.jackcan.webCrawler.FetchUrl;
import zhku.jackcan.webCrawler.FetchUrlFactory;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.fetchData.bo.FetchRequest;

public class Fetcher {
	private FetchRequest request;
	private String charset;
	private String termNo;
	private IAnalysiser analysiser;
	private FetchUrl fetchUrl = FetchUrlFactory.getFetchurl();
	public String getTermNo() {
		return termNo;
	}
	public Fetcher setTermNo(String termNo) {
		this.termNo = termNo;
		return this;
	}
	public IAnalysiser getAnalysiser() {
		return analysiser;
	}
	public Fetcher setAnalysiser(IAnalysiser analysiser) {
		this.analysiser = analysiser;
		return this;
	}
	public FetchRequest getRequest() {
		return request;
	}
	public Fetcher setRequest(FetchRequest request) {
		this.request = request;
		return this;
	}
	
	public List doFetch() throws FetchTimeoutException{
		String http="GET";
		String html=null;
		fetchUrl.setDecodeCharset(getCharset());
		fetchUrl.setCookies(request.getCookies());
		fetchUrl.setHeader("Referer",request.getReferer());
		if(request.getPostParams()!=null&&request.getPostData()==null){
			fetchUrl.setPostData(request.getPostParams());
			http="POST";
		}else if(request.getPostParams()==null&&request.getPostData()!=null){
			fetchUrl.setPostData(request.getPostData());
			http="POST";
		}
		if(http.equals("GET")){
			html=fetchUrl.get(request.getRequestUrl(), request.getReDoTimes());
		}else{
			html=fetchUrl.post(request.getRequestUrl(), request.getReDoTimes());
		}
		if(html==null) return null;
		return analysiser.doAnalysis(html);
	}
	
	
	public List doFetch(String termNo,FetchRequest request,IAnalysiser analysiser) throws FetchTimeoutException{
		return this.setAnalysiser(analysiser).setRequest(request).setTermNo(termNo).doFetch();
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.fetchUrl.setDecodeCharset(charset);
		this.charset = charset;
	}

}
