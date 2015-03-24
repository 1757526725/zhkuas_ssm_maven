package com.zhku.service.db.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Pagination;
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

	@Override
	public Pagination<Student> getStudentsByPagination(Pagination<Student> pagination) {
		Pagination<Student> paginationTemp = studentMapper.getStudentsByPagination(pagination);
		if(paginationTemp!=null){
			paginationTemp.setCurrentPage(pagination.getCurrentPage());
			paginationTemp.setPageSize(pagination.getPageSize());
		}
		return paginationTemp;
	}

	public StudentMapper getStudentMapper() {
		return studentMapper;
	}

	public void setStudentMapper(StudentMapper studentMapper) {
		this.studentMapper = studentMapper;
	}

}
