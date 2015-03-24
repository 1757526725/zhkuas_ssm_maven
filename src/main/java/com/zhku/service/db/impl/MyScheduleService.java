package com.zhku.service.db.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhku.bean.MySchedule;
import com.zhku.dao.MyScheduleMapper;
import com.zhku.service.db.IMyScheduleService;
@Service("myScheduleService")
public class MyScheduleService extends ServiceSupport implements IMyScheduleService{

	@Autowired
	private MyScheduleMapper myScheduleMapper;
	@Override
	public void addMySchedule(MySchedule mySchedule) {
		myScheduleMapper.addMySchedule(mySchedule);
	}

	@Override
	public void deleteMySchedule(MySchedule mySchedule) {
		myScheduleMapper.deleteMySchedule(mySchedule);		
	}

	@Override
	public void updateMySchedule(MySchedule mySchedule) {
		myScheduleMapper.updateMySchedule(mySchedule);
	}

	@Override
	public MySchedule getMyScheduleById(int msdid) {
		return myScheduleMapper.getMyScheduleById(msdid);
	}

	@Override
	public List<MySchedule> getMySchedulesByTermNoAndUid(String termNo, Integer uid) {
		Map<String, String> params = new HashMap<String,String>();
		params.put("termNo", termNo);
		params.put("uid", uid+"");
		return myScheduleMapper.getMySchedulesByTermNoAndUid(params);
	}

	public MyScheduleMapper getMyScheduleMapper() {
		return myScheduleMapper;
	}

	public void setMyScheduleMapper(MyScheduleMapper myScheduleMapper) {
		this.myScheduleMapper = myScheduleMapper;
	}

}
