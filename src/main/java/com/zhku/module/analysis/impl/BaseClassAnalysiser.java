package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Major;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;

public class BaseClassAnalysiser implements IAnalysiser {
	private String grade;
	private Major major;
	@Override
	public List doAnalysis(String html) {
		List<BaseClass> list = new ArrayList<BaseClass>();
		Element element = null;
		Elements options = null;
		element=HTMLUtil.getSelectorByName(html, Constants.HTML_ELEMENT_NAME.SELECT_NAME_BASECLASS.getValue());
		if (element != null) {
			options = element.children();
			BaseClass baseClass = null;
			for (Element e : options) {
				if (e.attr("value").equals("")||e.text()==null||e.text().equals(""))
					continue;
				baseClass = new BaseClass();
				baseClass.setName(HTMLUtil.cutName(e.text()));
				baseClass.setNo(e.attr("value"));
				baseClass.setGrade(getGrade());
				baseClass.setMajor(major);
				list.add(baseClass);
			}
		}
		return list;
	}
	public String getGrade() {
		return grade;
	}
	public BaseClassAnalysiser setGrade(String grade) {
		this.grade = grade;
		return this;
	}
	public Major getMajor() {
		return major;
	}
	public BaseClassAnalysiser setMajor(Major major) {
		this.major = major;
		return this;
	}
	
	

}
