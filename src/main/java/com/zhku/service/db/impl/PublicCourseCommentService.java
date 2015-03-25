package com.zhku.service.db.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.PublicCourseComment;
import com.zhku.dao.PublicCourseCommentMapper;
import com.zhku.service.db.IPublicCourseCommentService;

@Service("publicCourseCommentService")
public class PublicCourseCommentService implements IPublicCourseCommentService {
	@Autowired 
	private PublicCourseCommentMapper publicCourseCommentMapper;
	@Override
	public void addPublicCourseComment(PublicCourseComment publicCourseComment) {
		publicCourseCommentMapper.addPublicCourseComment(publicCourseComment);
	}
	@Override
	public void deletePublicCourseComment(
			PublicCourseComment publicCourseComment) {
		publicCourseCommentMapper.deletePublicCourseComment(publicCourseComment);
	}
	@Override
	public void updatePublicCourseComment(
			PublicCourseComment publicCourseComment) {
		publicCourseCommentMapper.updatePublicCourseComment(publicCourseComment);		
	}
	@Override
	public PublicCourseComment getPublicCourseCommentById(int pccid) {
		return publicCourseCommentMapper.getPublicCourseCommetById(pccid);
	}
	@Override
	public List<PublicCourseComment> getPublicCourseCommentByCno(String cNo) {
		return publicCourseCommentMapper.getPublicCourseCommetByCno(cNo);
	}
	@Override
	public List<PublicCourseComment> getPublicCourseCommentsByUid(int uid) {
		return publicCourseCommentMapper.getPublicCourseCommentsByUid(uid);
	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommentReplyByUid(int uid) {
		return publicCourseCommentMapper.getPublicCourseCommentReplyByUid(uid);
	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommentUnreadByUid(int uid) {
		return publicCourseCommentMapper.getPublicCourseCommentUnreadByUid(uid);
	}
	public PublicCourseCommentMapper getPublicCourseCommentMapper() {
		return publicCourseCommentMapper;
	}
	public void setPublicCourseCommentMapper(
			PublicCourseCommentMapper publicCourseCommentMapper) {
		this.publicCourseCommentMapper = publicCourseCommentMapper;
	}

	

}
