package com.zhku.bean;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author JackCan_Liao
 *班级类
 */
public class BaseClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1068349240675839367L;
	private int id ;
	private String no;
	private String name;
	private Major major;
	private String grade;
	private CampusArea campus;
	private List<TermCourse> courseList;
	public BaseClass() {
	}
	public BaseClass(String no, String name) {
		this.no = no;
		this.name = name;
	}


	public List<TermCourse> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<TermCourse> courseList) {
		this.courseList = courseList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Major getMajor() {
		return major;
	}
	public void setMajor(Major major) {
		this.major = major;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public CampusArea getCampus() {
		return campus;
	}
	public void setCampus(CampusArea campus) {
		this.campus = campus;
	}
	
	
}
