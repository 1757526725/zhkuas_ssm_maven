package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Teacher;
import com.zhku.exception.ObjectExistsException;

public interface ITeacherService {
	public void addTeacher(Teacher teacher) throws ObjectExistsException;
	public void deleteTeacher(Teacher teacher);
	public Teacher getTeacherById(int tId);
	public List<Teacher> getTeachers();
	public Teacher getTeacherByTno(String tNo);
	public void updateTeacher(Teacher teacher);
	public Teacher getTeacherByTnameNo(String tNameNo);
	public List<Teacher> getTeacherByTname(String teacher);
}
