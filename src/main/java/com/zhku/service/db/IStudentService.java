package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Student;

public interface IStudentService {
	public void addStudent(Student student);
	public void deleteStudent(Student student);
	public Student getStudentById(int sid);
	public List<Student> getStudents();
	public List<Student> getStudents(int pageNum,int pageSize,boolean needCountTotal);
	public Student getStudentBySno(String sno);
	public void updateStudent(Student student);
//	public Pagination<Student> getStudentsByPagination(Pagination<Student> pagination);
	public List<Student> getStudentsByKeyword(String keyword);
}
