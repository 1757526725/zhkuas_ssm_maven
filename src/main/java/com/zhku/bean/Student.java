package com.zhku.bean;

import java.io.Serializable;

/**
 * 学生类
 * 
 */
public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8534489709725426101L;
	private int id;
	private String sNo;
	private String name;
	private BaseClass classBelongTo;
	private String sex;
	public BaseClass getClassBelongTo() {
		return classBelongTo;
	}
	public void setClassBelongTo(BaseClass classBelongTo) {
		this.classBelongTo = classBelongTo;
	}
	
	public String getsNo() {
		return sNo;
	}
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
}
