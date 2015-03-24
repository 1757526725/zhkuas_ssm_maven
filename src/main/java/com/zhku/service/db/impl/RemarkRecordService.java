package com.zhku.service.db.impl;

import org.apache.ibatis.session.SqlSession;

import com.zhku.bean.RemarkRecord;
import com.zhku.dao.RemarkRecordMapper;
import com.zhku.service.db.IRemarkRecordService;

public class RemarkRecordService extends ServiceSupport implements
		IRemarkRecordService {

	@Override
	public void addRemarkRecord(RemarkRecord remarkReacord) {
		SqlSession session = null; 
        try{
           session = getSqlSessionFactory().openSession(); 
           RemarkRecordMapper remarkRecord =session.getMapper(RemarkRecordMapper.class);
           remarkRecord.addRemarkRecord(remarkReacord);
         session.commit();
        }catch(Exception e){ 
            e.printStackTrace();
        	throw new RuntimeException(e);
        }finally{ 
           session.close(); 
        }

	}

	@Override
	public void deleteRemarkRecord(RemarkRecord remarkReacord) {
		SqlSession session = null; 
        try{
           session = getSqlSessionFactory().openSession(); 
           RemarkRecordMapper remarkRecordDao =session.getMapper(RemarkRecordMapper.class);
           remarkRecordDao.deleteRemarkRecord(remarkReacord);
         session.commit();
        }catch(Exception e){ 
            e.printStackTrace();
        	throw new RuntimeException(e);
        }finally{ 
           session.close(); 
        }

		
	}

	@Override
	public void updateRemarkRecord(RemarkRecord remarkReacord) {
		SqlSession session = null; 
        try{
           session = getSqlSessionFactory().openSession(); 
           RemarkRecordMapper remarkRecordDao =session.getMapper(RemarkRecordMapper.class);
           remarkRecordDao.updateRemarkRecord(remarkReacord);
         session.commit();
        }catch(Exception e){ 
            e.printStackTrace();
        	throw new RuntimeException(e);
        }finally{ 
           session.close(); 
        }

	}

	@Override
	public RemarkRecord getRemarkRecordByCnoAndUid(RemarkRecord remarkRecord) {
		SqlSession session = null; 
        try{
           session = getSqlSessionFactory().openSession(); 
           RemarkRecordMapper remarkRecordDao =session.getMapper(RemarkRecordMapper.class);
          return remarkRecordDao.getRemarkRecordByCnoAndUid(remarkRecord);
        }catch(Exception e){ 
            e.printStackTrace();
        	throw new RuntimeException(e);
        }finally{ 
           session.close(); 
        }

	}

	@Override
	public RemarkRecord getRemarkRecordByRrid(int rrid) {
		SqlSession session = null; 
        try{
           session = getSqlSessionFactory().openSession(); 
           RemarkRecordMapper remarkRecordDao =session.getMapper(RemarkRecordMapper.class);
          return remarkRecordDao.getRemarkRecordByRrid(rrid);
        }catch(Exception e){ 
            e.printStackTrace();
        	throw new RuntimeException(e);
        }finally{ 
           session.close(); 
        }
	}

}
