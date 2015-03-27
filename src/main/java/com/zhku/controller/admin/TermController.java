package com.zhku.controller.admin;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhku.bean.Term;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.impl.TermService;
import com.zhku.web.Constants;
@Controller
@RequestMapping("/admin/term")
public class TermController {
	@Autowired
	private TermService termService;
	private final Log logger = LogFactory.getLog(TermController.class);
	@RequestMapping(method=RequestMethod.GET)
	public String show(Model model){
		List<Term> terms=termService.getTerms();
		model.addAttribute("terms", terms);
		return "admin_term";
	}
	
	@ResponseBody
	@RequestMapping(value="{termNos}",method=RequestMethod.DELETE)
	public Map<String,Object> del(@PathVariable String termNos){
		Map<String,Object> result =new HashMap<String,Object>();
		if(termNos==null||termNos.equals("")){
			result.put("state", 0);
			result.put("error", Constants.Error.REQUEST_PARAMS_ERROR.toMap());
			return result;
		}
		String [] arrayTermNos =termNos.split("-");
		try{
			termService.deleteTerms(arrayTermNos);
		}catch (Exception e) {
			result.put("state", 0);
			result.put("error", Constants.Error.DELETE_ERROR.toMap());
			return result;
		}
		
		result.put("state", 1);
		result.put("result", "删除成功!");
		return result;
	}
	@ResponseBody
	@RequestMapping(method=RequestMethod.POST)
	public Map<String,Object> add(String termName,String termNo,String beginDate,String endDate){
		Map<String,Object> result =new HashMap<String,Object>();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-mm-dd");
		//构造一个Term
		Term term =new Term();
		term.setName(termName);
		term.setNo(termNo);
		try {
			term.setBeginDate(dft.parse(beginDate));
			term.setEndDate(dft.parse(endDate));
		} catch (ParseException e) {
			logger.error("时间转换错误"+e.getMessage());
			result.put("state", 0);
			result.put("error", Constants.Error.FORM_DATE_FORMATE_ERROR.toMap());
			return result;
		}
		
		//写入数据
		try {
			termService.addTerm(term);
		} catch (ObjectExistsException e) {
			logger.error(term.getName()+"学期已经存在"+e.getMessage());
			result.put("state", 0);
			result.put("error", Constants.Error.DB_DATA_EXIST.toMap());
			return result;
		}
		
		result.put("state", 1);
		result.put("result", term);
		return result;
	}
	@ResponseBody
	@RequestMapping(method=RequestMethod.PUT)
	public Map<String,Object> update(int termId,String termName,String termNo,String beginDate,String endDate){
		Map<String,Object> result =new HashMap<String,Object>();
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		//构造一个Term
		Term term =new Term();
		term.setName(termName);
		term.setNo(termNo);
		term.setId(termId);
		try {
			term.setBeginDate(dft.parse(beginDate));
			term.setEndDate(dft.parse(endDate));
		} catch (ParseException e) {
			logger.error("时间转换错误"+e.getMessage());
			result.put("state", 0);
			result.put("error", Constants.Error.FORM_DATE_FORMATE_ERROR.toMap());
			return result;
		}
		//校验term
		if(term.getName()==null||term.getName().equals("")){
			result.put("state", 0);
			result.put("error", Constants.Error.TERM_NAME_NULL_ERROR.toMap());
			return result;
		}
		if(term.getNo()==null||term.getNo().equals("")){
			result.put("state", 0);
			result.put("error", Constants.Error.TERM_NO_NULL_ERROR.toMap());
			return result;
		}
		termService.updateTerm(term);
		result.put("state", 1);
		result.put("result", term);
		return result;
	}
	public TermService getTermService() {
		return termService;
	}

	public void setTermService(TermService termService) {
		this.termService = termService;
	}
}
