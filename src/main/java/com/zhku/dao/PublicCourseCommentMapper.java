package com.zhku.dao;

import java.util.List;

import com.zhku.bean.PublicCourseComment;

public interface PublicCourseCommentMapper {
	public void addPublicCourseComment(PublicCourseComment publicCourseComment);
	public void deletePublicCourseComment(PublicCourseComment publicCourseComment);
	public void updatePublicCourseComment(PublicCourseComment publicCourseComment);
	public PublicCourseComment getPublicCourseCommetById(int pccid);
	public List<PublicCourseComment> getPublicCourseCommetByCno(String cNo);
	public List<PublicCourseComment> getPublicCourseCommentsByUid(int uid);
	public List<PublicCourseComment> getPublicCourseCommentReplyByUid(int uid);
	public List<PublicCourseComment> getPublicCourseCommentUnreadByUid(int uid);
	
}
