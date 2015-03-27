package com.zhku.service.db;

import java.util.List;
import java.util.Map;

import com.zhku.bean.MySchedule;

public interface IMyScheduleService {
	public void addMySchedule(MySchedule mySchedule);
	public void deleteMySchedule(MySchedule mySchedule);
	public void updateMySchedule(MySchedule mySchedule);
	public MySchedule getMyScheduleById(int msdid);
	public List<MySchedule> getMySchedulesByTermNoAndUid(String termNo,Integer uid);
}
