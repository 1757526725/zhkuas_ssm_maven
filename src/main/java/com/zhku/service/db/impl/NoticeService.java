package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.Notice;
import com.zhku.dao.NoticeMapper;
import com.zhku.service.db.INoticeService;
@Service("noticeService")
public class NoticeService implements INoticeService {

	@Autowired
	private NoticeMapper noticeMapper;
	
	@Override
	public void addNotice(Notice notice) {
		noticeMapper.addNotice(notice);
	}

	@Override
	public void deleteNotice(Notice notice) {
		noticeMapper.deleteNotice(notice);
	}

	@Override
	public void updateNotice(Notice notice) {
		noticeMapper.updateNotice(notice);
	}

	@Override
	public Notice getNoticeById(int id) {
		return noticeMapper.getNoticeById(id);
	}

	@Override
	public Notice getNoticeByTermNo(String termNo) {
		return noticeMapper.getNoticeByTermNo(termNo);
	}

	public NoticeMapper getNoticeMapper() {
		return noticeMapper;
	}

	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

}
