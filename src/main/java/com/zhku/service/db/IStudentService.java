package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Pagination;
import com.zhku.bean.Student;

public interface IStudentService {
	public void addStudent(Student student);
	public void deleteStudent(Student student);
	public Student getStudentById(int sid);
	public List<Student> getStudents();
	public Student getStudentBySno(String sno);
	public void updateStudent(Student student);
	public Pagination<Student> getStudentsByPagination(Pagination<Student> pagination);
}
