package com.zhku.service.db;

import java.util.List;

import com.zhku.bean.PublicCourseComment;

public interface IPublicCourseCommentService {
	public void addPublicCourseComment(PublicCourseComment publicCourseComment);
	public void deletePublicCourseComment(PublicCourseComment publicCourseComment);
	public void updatePublicCourseComment(PublicCourseComment publicCourseComment);
	public PublicCourseComment getPublicCourseCommentById(int pccid);
	public List<PublicCourseComment> getPublicCourseCommentByCno(String cNo);
	public List<PublicCourseComment> getPublicCourseCommentsByUid(int uid);
	public List<PublicCourseComment> getPublicCourseCommentReplyByUid(int uid);
	public List<PublicCourseComment> getPublicCourseCommentUnreadByUid(int uid);

}
