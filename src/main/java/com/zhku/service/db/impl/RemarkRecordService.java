package com.zhku.service.db.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.RemarkRecord;
import com.zhku.dao.RemarkRecordMapper;
import com.zhku.service.db.IRemarkRecordService;

@Service("remarkRecordService")
public class RemarkRecordService implements IRemarkRecordService {

	@Autowired
	private RemarkRecordMapper remarkRecordMapper;
	public RemarkRecordMapper getRemarkRecordMapper() {
		return remarkRecordMapper;
	}

	public void setRemarkRecordMapper(RemarkRecordMapper remarkRecordMapper) {
		this.remarkRecordMapper = remarkRecordMapper;
	}

	@Override
	public void addRemarkRecord(RemarkRecord remarkReacord) {
		remarkRecordMapper.addRemarkRecord(remarkReacord);
	}

	@Override
	public void deleteRemarkRecord(RemarkRecord remarkReacord) {
		remarkRecordMapper.deleteRemarkRecord(remarkReacord);
	}

	@Override
	public void updateRemarkRecord(RemarkRecord remarkReacord) {
		remarkRecordMapper.updateRemarkRecord(remarkReacord);		
	}

	@Override
	public RemarkRecord getRemarkRecordByCnoAndUid(String cNo,Integer uid) {
		RemarkRecord remarkRecord = new RemarkRecord();
		remarkRecord.setcNo(cNo);
		remarkRecord.setUid(uid);
		return remarkRecordMapper.getRemarkRecordByCnoAndUid(remarkRecord );
	}

	@Override
	public RemarkRecord getRemarkRecordByRrid(int rrid) {
		return remarkRecordMapper.getRemarkRecordByRrid(rrid);
	}

	

}
