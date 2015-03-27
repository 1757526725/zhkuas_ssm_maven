package com.zhku.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.Term;
import com.zhku.bean.WebConfig;
import com.zhku.service.db.ITermService;
import com.zhku.service.db.IWebConfigService;

@Controller
@RequestMapping("/admin/settings")
public class SettingsController {
	@Autowired
	private IWebConfigService webConfigService;
	@Autowired
	private ITermService termService;
	/**
	 * 默认显示页
	 * @param request
	 * @return
	 */
	@RequestMapping("")
	public String show(HttpServletRequest request){
		WebConfig config = webConfigService.getWebConfig();
		List<Term> termList = termService.getTerms();
		request.setAttribute("config", config);
		request.setAttribute("termList", termList);
		return "admin_settings";
	}
	/**
	 * 更新系统配置,这里更新后应该立马更新系统的缓存
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="",method=RequestMethod.POST)
	public Map<String,String> saveSettings(HttpServletRequest request){
		String siteName = request.getParameter("siteName");
		String domain =  request.getParameter("domain");
		String keywords =  request.getParameter("keywords");
		String description = request.getParameter("description");
		String termNo = request.getParameter("term");
		WebConfig config = webConfigService.getWebConfig();
		if(config==null)
		config = new WebConfig();
		config.setCurrentTerm(new Term(termNo));
		config.setDescription(description);
		config.setDomain(domain);
		config.setKeyword(keywords);
		config.setSiteName(siteName);
		Map<String,String> retval= new HashMap<String,String>();
		webConfigService.updateWebConfig(config);
		retval.put("state", "1");
		retval.put("result", "更新成功!");
		return retval;
	}
	public IWebConfigService getWebConfigService() {
		return webConfigService;
	}
	public void setWebConfigService(IWebConfigService webConfigService) {
		this.webConfigService = webConfigService;
	}
	public ITermService getTermService() {
		return termService;
	}
	public void setTermService(ITermService termService) {
		this.termService = termService;
	}
}
