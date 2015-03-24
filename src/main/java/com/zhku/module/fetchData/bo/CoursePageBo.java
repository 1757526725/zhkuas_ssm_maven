package com.zhku.module.fetchData.bo;

import java.io.Serializable;

/**
 * 课程信息页对象
 * @author JackCan_Liao
 *
 */
public class CoursePageBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9158303357693154550L;
	private String name;
	private String no;
	private String englishName;
	private String nameNo;
	private String credits;
	private String courseType;
	private String qualityHours;
	private String teachingHours;
	private String experimentalHours;
	private String computerClassHours;
	private String otherClassHours;
	private String teachingProgram;
	private String organization;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnglishName() {
		return englishName;
	}
	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}
	public String getNameNo() {
		return nameNo;
	}
	public void setNameNo(String nameNo) {
		this.nameNo = nameNo;
	}
	public String getCredits() {
		return credits;
	}
	public void setCredits(String credits) {
		this.credits = credits;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getQualityHours() {
		return qualityHours;
	}
	public void setQualityHours(String qualityHours) {
		this.qualityHours = qualityHours;
	}
	public String getTeachingHours() {
		return teachingHours;
	}
	public void setTeachingHours(String teachingHours) {
		this.teachingHours = teachingHours;
	}
	public String getExperimentalHours() {
		return experimentalHours;
	}
	public void setExperimentalHours(String experimentalHours) {
		this.experimentalHours = experimentalHours;
	}
	public String getComputerClassHours() {
		return computerClassHours;
	}
	public void setComputerClassHours(String computerClassHours) {
		this.computerClassHours = computerClassHours;
	}
	public String getOtherClassHours() {
		return otherClassHours;
	}
	public void setOtherClassHours(String otherClassHours) {
		this.otherClassHours = otherClassHours;
	}
	public String getTeachingProgram() {
		return teachingProgram;
	}
	public void setTeachingProgram(String teachingProgram) {
		this.teachingProgram = teachingProgram;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	
	
}
