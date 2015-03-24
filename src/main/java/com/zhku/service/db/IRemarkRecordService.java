package com.zhku.service.db;

import com.zhku.bean.RemarkRecord;

public interface IRemarkRecordService {
	public void addRemarkRecord(RemarkRecord remarkReacord);
	public void deleteRemarkRecord(RemarkRecord remarkReacord);
	public void updateRemarkRecord(RemarkRecord remarkReacord);
	public RemarkRecord getRemarkRecordByCnoAndUid(RemarkRecord remarkRecord);
	public RemarkRecord getRemarkRecordByRrid(int rrid);
}
