package com.zhku.bean;
/**
 * 教务网账户 
 * @author JackCan
 */
public class Account {
	private int id;
	private String username;
	private String password;
	private CampusArea campus;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public CampusArea getCampus() {
		return campus;
	}
	public void setCampus(CampusArea campus) {
		this.campus = campus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
