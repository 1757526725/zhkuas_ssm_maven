package com.zhku.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 课程用户评论
 * @author JackCan
 *
 */
public class PublicCourseComment implements Serializable {
	
	private static final long serialVersionUID = -6094685259293256033L;
	private Integer id;
	private User commentUser;
	private User replyUser;
	private String cNo;
	private String content;
	private int parentId;
	private List<PublicCourseComment> commentChilds;
	private Date commentTime;
	private int state;
	private String courseName;
	public String getcNo() {
		return cNo;
	}

	public void setcNo(String cNo) {
		this.cNo = cNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<PublicCourseComment> getCommentChilds() {
		return commentChilds;
	}

	public void setCommentChilds(List<PublicCourseComment> commentChilds) {
		this.commentChilds = commentChilds;
	}

	public User getCommentUser() {
		return commentUser;
	}

	public void setCommentUser(User commentUser) {
		this.commentUser = commentUser;
	}

	public User getReplyUser() {
		return replyUser;
	}

	public void setReplyUser(User replyUser) {
		this.replyUser = replyUser;
	}
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
