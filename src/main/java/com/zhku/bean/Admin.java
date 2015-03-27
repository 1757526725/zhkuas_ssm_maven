package com.zhku.bean;

import java.io.Serializable;

public class Admin implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8872085601616306075L;
	private int id;
	private User user;
	private String password;
	private String username;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
