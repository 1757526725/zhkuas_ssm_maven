package com.zhku.bean;

import java.io.Serializable;
import java.util.List;

public class CampusArea implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 542356543136583202L;
	private int id;
	private String name;
	private List<SchoolBuilding> buildingList;  //教学楼列表 ，不一定有内容
	private List<BaseClass> classList;   //班级列表 ，不一定有内容
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
	public List<SchoolBuilding> getBuildingList() {
		return buildingList;
	}
	public void setBuildingList(List<SchoolBuilding> buildingList) {
		this.buildingList = buildingList;
	}
	public List<BaseClass> getClassList() {
		return classList;
	}
	public void setClassList(List<BaseClass> classList) {
		this.classList = classList;
	}
}
