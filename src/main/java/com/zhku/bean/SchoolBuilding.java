package com.zhku.bean;

import java.io.Serializable;
import java.util.List;

public class SchoolBuilding implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7455924991046402273L;
	private int id;
	private String name;
	private String no;
	private CampusArea campusArea;
	private List<Classroom> classrooms;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

	public List<Classroom> getClassrooms() {
		return classrooms;
	}
	public void setClassrooms(List<Classroom> classrooms) {
		this.classrooms = classrooms;
	}
	public CampusArea getCampusArea() {
		return campusArea;
	}
	public void setCampusArea(CampusArea campusArea) {
		this.campusArea = campusArea;
	}
}
