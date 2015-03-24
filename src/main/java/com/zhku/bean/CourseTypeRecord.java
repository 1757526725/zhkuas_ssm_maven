package com.zhku.bean;

import java.io.Serializable;

public class CourseTypeRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8109986778448312845L;
	private int id;
	private String cName; //course Name
	private int pctid; //publicCourseTypeId
	public int getPctid() {
		return pctid;
	}
	public void setPctid(int pctid) {
		this.pctid = pctid;
	}
	public String getcName() {
		return cName;
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
