package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.zhku.bean.Classroom;
import com.zhku.dao.ClassroomMapper;
import com.zhku.exception.ObjectExistsException;
import com.zhku.service.db.IClassroomService;
@Service("classroomService")
public class ClassroomService implements IClassroomService {
	@Autowired
	private ClassroomMapper classroomMapper;
	@Override
	public void addClassroom(Classroom classroom) throws ObjectExistsException {
		Classroom dbClassroom = classroomMapper.getClassroomByNo(classroom.getNo());
		if(dbClassroom!=null){
			throw new ObjectExistsException();
		}else{
			classroomMapper.addClassroom(classroom);
		}
	}

	@Override
	public void deleteClassroom(Classroom classroom) {
		classroomMapper.deleteClassroom(classroom);
	}

	@Override
	public void updateClassroom(Classroom classroom) {
		classroomMapper.updateClassroom(classroom);
	}

	@Override
	public Classroom getClassroomById(int id) {
		return classroomMapper.getClassroomById(id);
	}

	@Override
	public Classroom getClassroomByNo(String no) {
		return classroomMapper.getClassroomByNo(no);
	}

	@Override
	public Classroom getClassroomByName(String name) {
		return classroomMapper.getClassroomByName(name);
	}

	@Override
	public List<Classroom> getClassrooms() {
		return classroomMapper.getClassrooms();
	}

	@Override
	public List<Classroom> getClassroomsBySchoolBuildingNo(
			String schoolBuildingNo) {
		return classroomMapper.getClassroomsBySchoolBuildingNo(schoolBuildingNo);
	}

	public ClassroomMapper getClassroomMapper() {
		return classroomMapper;
	}

	public void setClassroomMapper(ClassroomMapper classroomMapper) {
		this.classroomMapper = classroomMapper;
	}

	@Override
	public List<Classroom> getClassrooms(int pageNum, int pageSize, boolean needCountTotal) {
		PageHelper.startPage(pageNum, pageSize, needCountTotal);
		return classroomMapper.getClassrooms();
	}



}
