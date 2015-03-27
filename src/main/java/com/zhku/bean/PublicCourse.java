package com.zhku.bean;

import java.io.Serializable;

/**
 * 公共选修类
 * @author JackCan_Liao
 *
 */
public class PublicCourse implements Serializable {
	private static final long serialVersionUID = -5539386280568284408L;
	private Integer id;
	private Integer campusId;
	private int availableNum;
	private TermCourse termCourse;
	
	public int getAvailableNum() {
		return availableNum;
	}
	public void setAvailableNum(int availableNum) {
		this.availableNum = availableNum;
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
	public Integer getCampusId() {
		return campusId;
	}
	public void setCampusId(Integer campusId) {
		this.campusId = campusId;
	}
}
