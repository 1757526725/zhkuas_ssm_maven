package com.zhku.bean;

import java.io.Serializable;

public class CourseTeacherRS implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1113583855414035639L;
	private int id;
	private String termNo;
	private String cNo;
	private String tNo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getcNo() {
		return cNo;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public String gettNo() {
		return tNo;
	}
	public void settNo(String tNo) {
		this.tNo = tNo;
	}
	
	
}
