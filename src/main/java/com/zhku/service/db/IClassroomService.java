package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.Classroom;
import com.zhku.exception.ObjectExistsException;

public interface IClassroomService {
	public void addClassroom(Classroom classroom) throws ObjectExistsException;
	public void deleteClassroom(Classroom classroom);
	public void updateClassroom(Classroom classroom);
	public Classroom getClassroomById(int id);
	public Classroom getClassroomByNo(String no);
	public Classroom getClassroomByName(String name);
	public List<Classroom> getClassrooms();
	public List<Classroom> getClassrooms(int pageNum,int pageSize,boolean needCountTotal);
	public List<Classroom> getClassroomsBySchoolBuildingNo(String schoolBuildingNo);
	public List<Classroom> getClassroomsByKeywork(String keyword);
	List<Classroom> getClassroomsByKeywork(String keyword, int pageNum, int pageSize, boolean needCountTotal);
}
