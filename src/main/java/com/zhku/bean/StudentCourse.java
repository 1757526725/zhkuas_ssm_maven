package com.zhku.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 学生课程类，保存，学生个人课表
 * @author JackCan_Liao
 *
 */
public class StudentCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1469503181434202531L;
	private int id;
	private User student;
	private List<TermCourse> termCourses;
	
	public List<TermCourse> getTermCourses() {
		return termCourses;
	}
	public void setTermCourses(List<TermCourse> termCourses) {
		this.termCourses = termCourses;
	}
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
