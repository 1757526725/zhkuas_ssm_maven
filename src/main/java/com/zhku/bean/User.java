package com.zhku.bean;

import java.io.Serializable;

/**
 * 用户类
 * @author JackCan_Liao
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1890862248872141249L;
	private int uid;
	private Student student;
	private String nickName;
	private String avatorUrl;
	private boolean isDIYAvater;
	private String email;
	private String description;
	private String sinaUid;
	private int state;
	public User() {
		super();
	}
	public User(int uid) {
		super();
		this.uid = uid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	


	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}


	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	/**
	 * @return the isDIYAvater
	 */
	public boolean getIsDIYAvater() {
		return isDIYAvater;
	}


	/**
	 * @param isDIYAvater the isDIYAvater to set
	 */
	public void setIsDIYAvater(boolean isDIYAvater) {
		this.isDIYAvater = isDIYAvater;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvatorUrl() {
		return avatorUrl;
	}


	public void setAvatorUrl(String avatorUrl) {
		this.avatorUrl = avatorUrl;
	}


	public String getSinaUid() {
		return sinaUid;
	}


	public void setSinaUid(String sinaUid) {
		this.sinaUid = sinaUid;
	}
	
	
}
