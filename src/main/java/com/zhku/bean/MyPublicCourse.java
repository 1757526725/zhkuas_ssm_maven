package com.zhku.bean;

import java.io.Serializable;

public class MyPublicCourse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5502378237953698246L;

	private int id;
	private int uid;
	private Term term;
	private String cNameNo; //html页面数据 课程名代号 用于转换为 课程号
	private Course course;
	private String score;
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getcNameNo() {
		return cNameNo;
	}
	public void setcNameNo(String cNameNo) {
		this.cNameNo = cNameNo;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	
	
	
	
}
