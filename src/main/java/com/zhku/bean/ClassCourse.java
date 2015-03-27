package com.zhku.bean;

import java.io.Serializable;

/**
 * 
 * @author JackCan_Liao
 *	班级课程类
 */
public class ClassCourse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5038858445303020336L;
	private int id;
	private int termCourseId;
	private BaseClass baseClass;
	public BaseClass getBaseClass() {
		return baseClass;
	}
	public void setBaseClass(BaseClass baseClass) {
		this.baseClass = baseClass;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTermCourseId() {
		return termCourseId;
	}
	public void setTermCourseId(int termCourseId) {
		this.termCourseId = termCourseId;
	}
}
