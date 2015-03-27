package com.zhku.bean;

import java.io.Serializable;

public class PublicCourseOption implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 965720564241927977L;
	private Integer id;
	private String termNo;
	private CampusArea campus;
	private String cNo;
	private String optionHtml;
	/**
	 * @return the cNo
	 */
	public String getcNo() {
		return cNo;
	}
	/**
	 * @param cNo the cNo to set
	 */
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	/**
	 * @return the optionHtml
	 */
	public String getOptionHtml() {
		return optionHtml;
	}
	/**
	 * @param optionHtml the optionHtml to set
	 */
	public void setOptionHtml(String optionHtml) {
		this.optionHtml = optionHtml;
	}
	
	/**
	 * @return the termNo
	 */
	public String getTermNo() {
		return termNo;
	}
	/**
	 * @param termNo the termNo to set
	 */
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public CampusArea getCampus() {
		return campus;
	}
	public void setCampus(CampusArea campus) {
		this.campus = campus;
	}


}
