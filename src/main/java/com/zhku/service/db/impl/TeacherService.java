package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.Teacher;
import com.zhku.dao.TeacherMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.ITeacherService;

@Service("teacherService")
public class TeacherService implements ITeacherService {
	@Autowired
	private TeacherMapper teacherMapper;

	@Override
	public void addTeacher(Teacher teacher) throws ObjectExistsException {
		Teacher dbTeacher = teacherMapper.getTeacherByTno(teacher.getNo().trim());
		if (dbTeacher != null) {
			throw new ObjectExistsException();
		} else {
			teacherMapper.addTeacher(teacher);
		}
	}

	@Override
	public void deleteTeacher(Teacher teacher) {
		teacherMapper.deleteTeacher(teacher);
	}

	@Override
	public Teacher getTeacherById(int tId) {
		return teacherMapper.getTeacherById(tId);
	}

	@Override
	public List<Teacher> getTeachers() {
		return teacherMapper.getTeachers();
	}

	@Override
	public Teacher getTeacherByTno(String tNo) {
		return teacherMapper.getTeacherByTno(tNo);
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		teacherMapper.updateTeacher(teacher);
	}

	@Override
	public Teacher getTeacherByTnameNo(String tNameNo) {
		return teacherMapper.getTeacherByTnameNo(tNameNo);
	}

	public TeacherMapper getTeacherMapper() {
		return teacherMapper;
	}

	public void setTeacherMapper(TeacherMapper teacherMapper) {
		this.teacherMapper = teacherMapper;
	}

	@Override
	public List<Teacher> getTeacherByTname(String name) {
		return this.teacherMapper.getTeacherByTname(name);
	}

	@Override
	public List<Teacher> getTeachers(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize, needCountTotal);
		return this.teacherMapper.getTeachers();
	}

}
