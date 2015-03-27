package com.zhku.bean;

import java.io.Serializable;

public class Organization implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3266241292265242060L;
	public final static int TYPE_ORGANIZATION=2;
	private int id ;
	private String name;
	private String no;
	private int typeId;
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
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
}
