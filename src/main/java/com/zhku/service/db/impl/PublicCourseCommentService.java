package com.zhku.service.db.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.zhku.bean.PublicCourseComment;
import com.zhku.dao.PublicCourseCommentMapper;
import com.zhku.service.db.IPublicCourseCommentService;
import com.zhku.web.Constants;

public class PublicCourseCommentService extends ServiceSupport implements
		IPublicCourseCommentService {

	@Override
	public void addPublicCourseComment(PublicCourseComment publicCourseComment) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			publicCourseCommentDao.addPublicCourseComment(publicCourseComment);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

	@Override
	public void deletePublicCourseComment(
			PublicCourseComment publicCourseComment) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			if (publicCourseComment.getParentId() == 0 && publicCourseComment.getCommentChilds().size()!=0) {
				publicCourseComment.setContent("该评论已经被删除");
				publicCourseComment.setState(Constants.CommentState.DELETED
						.getState());
				publicCourseCommentDao
						.updatePublicCourseComment(publicCourseComment);
			} else {
				publicCourseCommentDao
						.deletePublicCourseComment(publicCourseComment);
			}
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public void updatePublicCourseComment(
			PublicCourseComment publicCourseComment) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			publicCourseCommentDao
					.updatePublicCourseComment(publicCourseComment);
			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public PublicCourseComment getPublicCourseCommetById(int pccid) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			return publicCourseCommentDao.getPublicCourseCommetById(pccid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommetByCno(String cNo) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			return publicCourseCommentDao.getPublicCourseCommetByCno(cNo);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommentsByUid(int uid) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			return publicCourseCommentDao.getPublicCourseCommentsByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommentReplyByUid(int uid) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			return publicCourseCommentDao.getPublicCourseCommentReplyByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

	@Override
	public List<PublicCourseComment> getPublicCourseCommentUnreadByUid(int uid) {
		SqlSession session = null;
		try {
			session = getSqlSessionFactory().openSession();
			PublicCourseCommentMapper publicCourseCommentDao = session
					.getMapper(PublicCourseCommentMapper.class);
			return publicCourseCommentDao.getPublicCourseCommentUnreadByUid(uid);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}

	}

}
