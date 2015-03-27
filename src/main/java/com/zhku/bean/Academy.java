package com.zhku.bean;

import java.io.Serializable;
import java.util.List;

public class Academy extends Organization implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2349616709302443931L;
	public final static int TYPE_ACADMEY=1;
	private List<Major> majorList;
	public boolean validate(){
		if(this.getTypeId()==TYPE_ACADMEY) return true;
		return false;
	}
	public List<Major> getMajorList() {
		return majorList;
	}
	public void setMajorList(List<Major> majorList) {
		this.majorList = majorList;
	}
}
