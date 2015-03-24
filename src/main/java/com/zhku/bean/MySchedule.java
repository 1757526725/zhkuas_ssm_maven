package com.zhku.bean;

import java.io.Serializable;

public class MySchedule implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8254098119622724441L;
	private Integer id;
	private Integer termCourseId;
	private int uid;
	private int state;
	private String termNo;
	private TermCourse termCourse;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public TermCourse getTermCourse() {
		return termCourse;
	}
	public void setTermCourse(TermCourse termCourse) {
		this.termCourse = termCourse;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTermCourseId() {
		return termCourseId;
	}
	public void setTermCourseId(Integer termCourseId) {
		this.termCourseId = termCourseId;
	}
	
	
}
