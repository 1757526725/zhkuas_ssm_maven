package com.zhku.bean;

import java.io.Serializable;
import java.util.Date;

public class Notice implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2034777860221132120L;
	private int id;
	private Term term;
	private String content;
	private Date createTime;
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
