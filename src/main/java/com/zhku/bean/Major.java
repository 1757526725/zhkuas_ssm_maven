package com.zhku.bean;

import java.io.Serializable;

public class Major implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4880732533853672395L;
	private int id;
	private String name;
	private String no;
	private Academy academy;
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
	public Academy getAcademy() {
		return academy;
	}
	public void setAcademy(Academy academy) {
		this.academy = academy;
	}
	
}
