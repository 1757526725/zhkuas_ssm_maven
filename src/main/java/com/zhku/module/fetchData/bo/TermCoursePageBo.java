package com.zhku.module.fetchData.bo;

import com.zhku.bean.Course;
import com.zhku.bean.Term;

public class TermCoursePageBo {
	private Term term;
	private String courseClassNo;
	private Course course;
	private String courseType; //TODO
	private String studentNum;
	private String classNames;
	private String periods;	
	private String dayAndSection;
	private String classRoom;
	private String teacher;

	public String getCourseClassNo() {
		return courseClassNo;
	}
	public void setCourseClassNo(String courseClassNo) {
		this.courseClassNo = courseClassNo;
	}

	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getClassRoom() {
		return classRoom;
	}
	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public String getDayAndSection() {
		return dayAndSection;
	}
	public void setDayAndSection(String dayAndSection) {
		this.dayAndSection = dayAndSection;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getClassNames() {
		return classNames;
	}
	public void setClassNames(String classNames) {
		this.classNames = classNames;
	}


	
}
