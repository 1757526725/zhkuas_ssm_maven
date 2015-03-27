package com.zhku.bean;

import java.io.Serializable;

public class CourseListPage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2315731512815727518L;
	private Integer id;
	private String majorNo;
	private String pageContent;
	public String getMajorNo() {
		return majorNo;
	}
	public void setMajorNo(String majorNo) {
		this.majorNo = majorNo;
	}
	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
