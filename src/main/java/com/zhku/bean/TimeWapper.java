package com.zhku.bean;

import java.io.Serializable;
import java.util.List;

public class TimeWapper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1676838842524824919L;
	private List<Integer> periodList;
	private List<Integer> sectionList;
	private Integer week;
	public TimeWapper(List<Integer> periodList, List<Integer> sectionList,
			Integer week) {
		super();
		this.periodList = periodList;
		this.sectionList = sectionList;
		this.week = week;
	}
	public TimeWapper() {
		super();
	}
	public List<Integer> getPeriodList() {
		return periodList;
	}
	public void setPeriodList(List<Integer> periodList) {
		this.periodList = periodList;
	}
	public List<Integer> getSectionList() {
		return sectionList;
	}
	public void setSectionList(List<Integer> sectionList) {
		this.sectionList = sectionList;
	}
	public Integer getWeek() {
		return week;
	}
	public void setWeek(Integer week) {
		this.week = week;
	}
	
	
}
