package com.zhku.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import zhku.jackcan.webCrawler.BasicNameValuePair;
import zhku.jackcan.webCrawler.NameValuePair;
import zhku.jackcan.webCrawler.exception.FetchTimeoutException;

import com.zhku.bean.Term;
import com.zhku.bean.User;
import com.zhku.controller.util.SecurityUtil;
import com.zhku.module.fetchData.FetchHelper;
import com.zhku.service.db.ITermService;
import com.zhku.utils.WebUtils;
import com.zhku.web.Constants.Error;
import com.zhku.web.Constants.SessionKey;

@Controller
@RequestMapping("/network")
public class OneKeySubmitController {
	@Autowired
	private ITermService termService;
	@ResponseBody
	@RequestMapping(value ="/course/public/oneKey", method=RequestMethod.POST)
	public Map<String,Object> submit(HttpSession session,HttpServletRequest request){
		if(!SecurityUtil.checkLogin(session)){
			return  WebUtils.webJsonError(Error.LOGIN_OUT_TIME);
		}
		String sNo = (String) session.getAttribute(SessionKey.STUDENT_NO.toString());
		if(sNo==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		String cookie = (String) session.getAttribute(SessionKey.STUDENT_COOKIE.toString()+sNo);
		if(cookie==null){
			return WebUtils.webJsonError(Error.ACADEMY_SYSTEM_LOGIN_OUT_TIME);
		}
		Term term = (Term) session.getAttribute("term");
		if(term==null){
			term = termService.getCurrentTerm();
		}
		String cNo = request.getParameter("cNo");
		String tName = request.getParameter("tName");
		String courseName = request.getParameter("courseName").trim();
		String credit = request.getParameter("credit");
		String formValue = request.getParameter("formValue");
		String chkKC1 = cNo + "%0001|01|03|02|" + credit + "|0001|0|"
				+ courseName + "|05";
		String chkSKBJ1 = formValue;
		String sel_xnxq = term.getNo();
		String mcount = "1";
		String sel_lx = "2";
		String skbjArray[] = chkSKBJ1.split(";");
		String id = "TTT";
		for (int i = 0; i < skbjArray.length; ++i) {
			id += "," + skbjArray[i] + "#" + chkKC1;
		}
		List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 创建参数队列
		formParams.add(new BasicNameValuePair("chkKC1", chkKC1));
		formParams.add(new BasicNameValuePair("chkSKBJ1", chkSKBJ1));
		formParams.add(new BasicNameValuePair("sel_xnxq", sel_xnxq));
		formParams.add(new BasicNameValuePair("mcount", mcount));
		formParams.add(new BasicNameValuePair("sel_lx", sel_lx));
		formParams.add(new BasicNameValuePair("id", id));
		String result=null;
		try {
			result = new FetchHelper().submitCourse(cookie, formParams);
		} catch (FetchTimeoutException e) {
			return WebUtils.webJsonError(Error.SUBMIT_TIME_OUT);
		}
		if(result ==null){
			return WebUtils.webJsonError(Error.SUBMIT_TIME_OUT);
		}
		return WebUtils.webJsonResult(result);
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
