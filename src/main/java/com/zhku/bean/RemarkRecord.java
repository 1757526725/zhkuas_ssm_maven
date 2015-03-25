package com.zhku.bean;

import java.io.Serializable;

public class RemarkRecord implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8071537313137764846L;
	private Integer id;
	private String cNo;
	private int uid; 
	/*
	 * 1.0 表示 未评论 
	 * 2.1表示已点赞
	 * 3.2表示已点踩
	 */
	private int remarkItem;
	public RemarkRecord() {
		super();
	}
	public RemarkRecord(String cNo, int uid) {
		super();
		this.cNo = cNo;
		this.uid = uid;
	}

	public String getcNo() {
		return cNo;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRemarkItem() {
		return remarkItem;
	}
	public void setRemarkItem(int remarkItem) {
		this.remarkItem = remarkItem;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
