package com.zhku.bean;

import java.io.Serializable;

/**
 * 公共选修，课程类型，分人文，科技，外语，艺术，
 * @author JackCan_Liao
 *
 */
public class PublicCourseType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2895479956028677124L;
	private Integer id;
	private String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
