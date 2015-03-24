package com.zhku.bean;

import java.io.Serializable;

/*
 * 老师类
 */
public class Teacher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5104461644154048707L;
	private int id;
	private String no;
	private String name;
	private String nameNo;
	public Teacher() {
		super();
	}
	public Teacher(String no, String name) {
		super();
		this.no = no;
		this.setName(name);
	}

	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	@Override
	public boolean equals(Object obj) {
		if(no.equals(no)) return true;
		return false;
	}
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
	public String getNameNo() {
		return nameNo;
	}
	public void setNameNo(String nameNo) {
		this.nameNo = nameNo;
	}
	
	
	
}
