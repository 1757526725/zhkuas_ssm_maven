package com.zhku.bean;

import java.io.Serializable;

public class Classroom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4401680718264581052L;
	private int id;
	private String name;
	private String no;
	private SchoolBuilding schoolBuilding;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SchoolBuilding getSchoolBuilding() {
		return schoolBuilding;
	}
	public void setSchoolBuilding(SchoolBuilding schoolBuilding) {
		this.schoolBuilding = schoolBuilding;
	}

}
