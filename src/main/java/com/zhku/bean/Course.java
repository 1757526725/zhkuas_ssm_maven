package com.zhku.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 课程类
 * @author JackCan_Liao
 *
 */
public class Course implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9158303357693154550L;
	private int id;
	private String no;
	private String name;
	private String englishName;
	private String nameNo;
	private double credits;
	private String courseType;
	private double qualityHours;
	private double teachingHours;
	private double experimentalHours;
	private double computerClassHours;
	private double otherClassHours;
	private String teachingProgram;
	private Organization organization;
	private PublicCourseProfiles courseProfiles;
	private List<PublicCourseComment> commentList;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
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
	public double getCredits() {
		return credits;
	}
	public void setCredits(double credits) {
		this.credits = credits;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public double getQualityHours() {
		return qualityHours;
	}
	public void setQualityHours(double qualityHours) {
		this.qualityHours = qualityHours;
	}
	public double getTeachingHours() {
		return teachingHours;
	}
	public void setTeachingHours(double teachingHours) {
		this.teachingHours = teachingHours;
	}
	public double getExperimentalHours() {
		return experimentalHours;
	}
	public void setExperimentalHours(double experimentalHours) {
		this.experimentalHours = experimentalHours;
	}
	public double getComputerClassHours() {
		return computerClassHours;
	}
	public void setComputerClassHours(double computerClassHours) {
		this.computerClassHours = computerClassHours;
	}
	public double getOtherClassHours() {
		return otherClassHours;
	}
	public void setOtherClassHours(double otherClassHours) {
		this.otherClassHours = otherClassHours;
	}
	public String getTeachingProgram() {
		return teachingProgram;
	}
	public void setTeachingProgram(String teachingProgram) {
		this.teachingProgram = teachingProgram;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public PublicCourseProfiles getCourseProfiles() {
		return courseProfiles;
	}
	public void setCourseProfiles(PublicCourseProfiles courseProfiles) {
		this.courseProfiles = courseProfiles;
	}
	public List<PublicCourseComment> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<PublicCourseComment> commentList) {
		this.commentList = commentList;
	}
	
	
}
