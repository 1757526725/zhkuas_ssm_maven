package com.zhku.bean;

import java.io.Serializable;

public class WebConfig implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7518518554051704635L;
	private int id;
	private String siteName;
	private String domain;
	private String description;
	private String keyword;
	private String webfooterInfo;
	private Term   currentTerm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getWebfooterInfo() {
		return webfooterInfo;
	}
	public void setWebfooterInfo(String webfooterInfo) {
		this.webfooterInfo = webfooterInfo;
	}
	public Term getCurrentTerm() {
		return currentTerm;
	}
	public void setCurrentTerm(Term currentTerm) {
		this.currentTerm = currentTerm;
	}
}
