package com.zhku.dao;

import com.zhku.bean.RemarkRecord;

public interface RemarkRecordMapper {
	public void addRemarkRecord(RemarkRecord remarkReacord);
	public void deleteRemarkRecord(RemarkRecord remarkReacord);
	public void updateRemarkRecord(RemarkRecord remarkReacord);
	public RemarkRecord getRemarkRecordByCnoAndUid(RemarkRecord remarkRecord);
	public RemarkRecord getRemarkRecordByRrid(int rrid);
}
