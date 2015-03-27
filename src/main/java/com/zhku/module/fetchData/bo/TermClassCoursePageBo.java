package com.zhku.module.fetchData.bo;

import com.zhku.bean.BaseClass;
import com.zhku.bean.Term;

public class TermClassCoursePageBo {
	private Term term;
	private BaseClass baseClass;
	private String course;
	private String credit;
	private String teacher;
	private String examType;
	private String courseClassNo;
	private String studentNum;
	private String periods;
	private String dayAndsection;
	private String classroom;
	public Term getTerm() {
		return term;
	}
	public void setTerm(Term term) {
		this.term = term;
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
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public BaseClass getBaseClass() {
		return baseClass;
	}
	public void setBaseClass(BaseClass baseClass) {
		this.baseClass = baseClass;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getDayAndsection() {
		return dayAndsection;
	}
	public void setDayAndsection(String dayAndsection) {
		this.dayAndsection = dayAndsection;
	}
	
}
