package com.zhku.module.analysis.impl;

import java.util.List;
import java.util.Map;

import com.zhku.module.analysis.IAnalysiser;

public class SmartTableAnalysiser<T> implements IAnalysiser<T>{

	private Map<String,String> tableHeader;
	
	public Map<String, String> getTableHeader() {
		return tableHeader;
	}

	public void setTableHeader(Map<String, String> tableHeader) {
		this.tableHeader = tableHeader;
	}

	@Override
	public List<T> doAnalysis(String html) {
		return null;
	}

}
