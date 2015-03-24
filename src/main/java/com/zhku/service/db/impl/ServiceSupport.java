package com.zhku.service.db.impl;

import java.io.IOException;
import java.io.Reader;
import java.util.logging.Logger;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class ServiceSupport {
	private static SqlSessionFactory sqlMapper = null;
	private static Logger logger = Logger.getLogger(ServiceSupport.class
			.getName());

	public SqlSessionFactory getSqlSessionFactory() {
		Reader reader =null;
		if (sqlMapper == null) {
			try {
				String resource = "sqlMapConfig.xml";
				reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				
			} catch (Exception e) {
				logger.info(e.getMessage());
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					logger.info(e.getMessage());
				}
			}

			
		}
		return sqlMapper;
	}
	
	public  void closeSession(SqlSession session) {
		if(session!=null)
			session.close();
	}
}
