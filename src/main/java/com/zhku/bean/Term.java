package com.zhku.bean;

import java.io.Serializable;
import java.util.Date;

public class Term implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1577463651856665663L;
	private int id;
	private String name;
	private String no;
	private Date beginDate;
	private Date endDate;
	private boolean isCurrent;
	private boolean status;
	public Term(String termNo) {
		no=termNo;
	}
	public Term() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	public boolean isCurrent() {
		return isCurrent;
	}
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
}
