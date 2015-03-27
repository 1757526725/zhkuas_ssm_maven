package com.zhku.bean;

import java.io.Serializable;

/**
 * 公共选修属性类，评价信息等
 * @author JackCan_Liao
 *
 */
public class PublicCourseProfiles implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3777076364596262970L;
	private Integer id;
	private String cNo;
	private PublicCourseType pcType;
	private String recommend;
	private String evaluation;
	private int goodCount;
	private int badCount;
	public String getcNo() {
		return cNo;
	}
	public void setcNo(String cNo) {
		this.cNo = cNo;
	}
	public PublicCourseType getPcType() {
		return pcType;
	}
	public void setPcType(PublicCourseType pcType) {
		this.pcType = pcType;
	}
	public String getRecommend() {
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(int goodCount) {
		this.goodCount = goodCount;
	}
	public int getBadCount() {
		return badCount;
	}
	public void setBadCount(int badCount) {
		this.badCount = badCount;
	}
	
	
}
