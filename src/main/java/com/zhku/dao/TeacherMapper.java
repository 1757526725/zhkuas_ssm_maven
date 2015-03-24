package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Teacher;

public interface TeacherMapper {
	public void addTeacher(Teacher teahcer);
	public void deleteTeacher(Teacher teacher);
	public Teacher getTeacherById(int tId);
	public List<Teacher> getTeachers();
	public Teacher getTeacherByTno(String tNo);
	public void updateTeacher(Teacher teacher);
	public Teacher getTeacherByTnameNo(String tNameNo);
	public List<Teacher> getTeacherByTname(String name);
}
