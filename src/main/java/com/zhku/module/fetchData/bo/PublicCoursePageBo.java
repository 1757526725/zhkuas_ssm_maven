package com.zhku.module.fetchData.bo;

import com.zhku.bean.CampusArea;
import com.zhku.bean.Course;
import com.zhku.bean.Term;

public class PublicCoursePageBo {
	private Term term;
	private CampusArea campusArea;
	private String course;
	private String teacher;
	private String courseClassNo;
	private String studentNum;
	private String periods;	
	private String dayAndSection;
	private String classRoom;
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public CampusArea getCampusArea() {
		return campusArea;
	}
	public void setCampusArea(CampusArea campusArea) {
		this.campusArea = campusArea;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getCourseClassNo() {
		return courseClassNo;
	}
	public void setCourseClassNo(String courseClassNo) {
		this.courseClassNo = courseClassNo;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getDayAndSection() {
		return dayAndSection;
	}
	public void setDayAndSection(String dayAndSection) {
		this.dayAndSection = dayAndSection;
	}
	public String getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	
	
	
}
