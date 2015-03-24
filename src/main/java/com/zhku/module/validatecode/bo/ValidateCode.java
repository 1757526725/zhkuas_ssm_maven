package com.zhku.module.validatecode.bo;

import java.io.Serializable;
import java.util.Date;

public class ValidateCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1183298372173523416L;
	private int id;
	private String cookie;
	private byte[] image;
	private Date time;
	private boolean state;
	
	public void setState(boolean state){
		this.state=state;
	}
	
	public boolean getState(){
		return state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCookie() {
		return cookie;
	}
	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
