package com.zhku.bean;

public class CampusAreaClassRS {
	private Integer  id ;
	private String classNo;
	private Integer campusId;
	public String getClassNo() {
		return classNo;
	}
	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}
	public Integer getCampusId() {
		return campusId;
	}
	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
