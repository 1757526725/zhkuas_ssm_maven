package com.zhku.module.fetchData.bo;

import com.zhku.bean.Teacher;
import com.zhku.bean.Term;

public class TermTeacherCoursePageBo {
	private Term term;
	private Teacher teacher;
	private String course;
	private String credit;
	private String teachingWay;
	private String courseType;
	private String courseClassNo;
	private String className;
	private String studentNum;
	private String time;
	private String classroom;
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCredit() {
		return credit;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getTeachingWay() {
		return teachingWay;
	}
	public void setTeachingWay(String teachingWay) {
		this.teachingWay = teachingWay;
	}
	public String getCourseType() {
		return courseType;
	}
	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}
	public String getCourseClassNo() {
		return courseClassNo;
	}
	public void setCourseClassNo(String courseClassNo) {
		this.courseClassNo = courseClassNo;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getStudentNum() {
		return studentNum;
	}
	public void setStudentNum(String studentNum) {
		this.studentNum = studentNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	
}
