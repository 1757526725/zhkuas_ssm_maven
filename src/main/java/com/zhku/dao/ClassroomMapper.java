package com.zhku.dao;

import java.util.List;

import com.zhku.bean.Classroom;

public interface ClassroomMapper {
	public void addClassroom(Classroom classroom);
	public void deleteClassroom(Classroom classroom);
	public void updateClassroom(Classroom classroom);
	public Classroom getClassroomById(int id);
	public Classroom getClassroomByNo(String no);
	public Classroom getClassroomByName(String name);
	public List<Classroom> getClassrooms();
	public List<Classroom> getClassroomsBySchoolBuildingNo(String schoolBuildingNo);
}
