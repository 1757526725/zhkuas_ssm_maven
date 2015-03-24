package com.zhku.module.analysis.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zhku.bean.BaseClass;
import com.zhku.module.analysis.IAnalysiser;
import com.zhku.module.bo.KeyValue;
import com.zhku.module.utils.HTMLUtil;
import com.zhku.web.Constants;

public class SelectorValueAnalysiser implements IAnalysiser{
	private String selecterName;
	@Override
	public List doAnalysis(String html) {
		List<KeyValue> list = new ArrayList<KeyValue>();
		Element element = null;
		Elements options = null;
		element=HTMLUtil.getSelectorByName(html,getSelecterName());
		if (element != null) {
			options = element.children();
			for (Element e : options) {
				if (e.val().equals("")||e.text().equals(""))
					continue;
				list.add(new KeyValue(e.val(),e.text()));
			}
		}
		return list;
	}
	public String getSelecterName() {
		return selecterName;
	}
	public IAnalysiser setSelecterName(String selecterName) {
		this.selecterName = selecterName;
		return this;
	}
	
	
}
