package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Pagination;
import com.zhku.bean.Student;

public interface StudentMapper {
	public void addStudent(Student student);
	public void deleteStudent(Student student);
	public void updateStudent(Student student);
	public Student getStudentById(int sid);
	public Student getStudentBySno(String sno);
	public List<Student> getStudents();
	public Pagination<Student> getStudentsByPagination(Pagination<Student> pagination);
	public List<Student> getStudentsByKeyword(String keyword);
}
