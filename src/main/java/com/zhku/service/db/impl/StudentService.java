package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.Student;
import com.zhku.dao.StudentMapper;
import com.zhku.service.db.IStudentService;
@Service("studentService")
public class StudentService implements IStudentService{
	@Autowired
	private StudentMapper studentMapper;
	@Override
	public void addStudent(Student student) {
		studentMapper.addStudent(student);
	}

	@Override
	public void deleteStudent(Student student) {
		studentMapper.deleteStudent(student);
	}

	@Override
	public Student getStudentById(int sid) {
		return studentMapper.getStudentById(sid);
	}

	@Override
	public List<Student> getStudents() {
		return studentMapper.getStudents();
	}

	@Override
	public Student getStudentBySno(String sno) {
		return studentMapper.getStudentBySno(sno);
	}

	@Override
	public void updateStudent(Student student) {
		studentMapper.updateStudent(student);
	}


	public StudentMapper getStudentMapper() {
		return studentMapper;
	}

	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}

	@Override
	public List<Student> getStudents(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize, needCountTotal);
		return studentMapper.getStudents();
	}

	@Override
	public List<Student> getStudentsByKeyword(String keyword) {
		return studentMapper.getStudentsByKeyword(keyword);
	}

	@Override
	public List<Student> getStudentsByClassNo(String classNo) {
		return studentMapper.getStudentsByClassNo(classNo);
	}

	@Override
	public List<Student> getStudentsByClassNo(String classNo, Integer pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize, needCountTotal);
		return studentMapper.getStudentsByClassNo(classNo);
	}

}
