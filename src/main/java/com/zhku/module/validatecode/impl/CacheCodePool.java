package com.zhku.module.validatecode.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.zhku.module.validatecode.CodePool;
import com.zhku.module.validatecode.bo.ValidateCode;
import com.zhku.service.network.IWebLogin;
import com.zhku.service.network.impl.WebLogin;
import com.zhku.tools.cache.Memcache;
import com.zhku.tools.cache.MemcacheFactory;

public class CacheCodePool implements CodePool {

	private List<ValidateCode> pool = null;
	private final static String POOL_KEY = "codePool";
	private final static long OVERTIME = 30 * 60 * 1000;// 30分钟
	private int max_size = 200;
	private int temp_size = 50;
	private int per_size=15;
	private Memcache cache = MemcacheFactory.createMemcache();
	private static Logger logger =Logger.getLogger(CacheCodePool.class.getName());
	private IWebLogin webLogin =new WebLogin();
	public CacheCodePool() {
		pool =  (List<ValidateCode>) cache.get(POOL_KEY);
		if (pool == null) {
			pool = new ArrayList<ValidateCode>();
			cache.add(POOL_KEY, pool);
		}
	}

	@Override
	public void emptyPool() {
		cache.delete(POOL_KEY);
	}

	@Override
	public int getAvailableNum() {
		List<ValidateCode> temp_pool = (List<ValidateCode>) cache
				.get(POOL_KEY);
		if (temp_pool == null)
			return 0;
		return temp_pool.size();
	}

	@Override
	public ValidateCode getValidateCode() {
		// Map<String, ValidateCode> temp_pool=(Map<String, ValidateCode>)
		// cache.get(POOL_KEY);
		ValidateCode vCode = null;
		if (pool != null) {
			for (ValidateCode validateCode: pool) {
				if ((new Date().getTime() -validateCode.getTime()
						.getTime()) < OVERTIME) {
					vCode =validateCode;
					removeValidateCode(validateCode);
					break;
				}else{
					System.out.println("sssssssssssssss");
				}
			}
		}
		if(vCode==null){
			logger.warn("验证码缓冲池已经空了");
//			vCode=validateCodeFetcher.getValidateCode();
			System.out.println("验证码缓冲池已经空了");
		}
		logger.info("可用验证码还有"+pool.size()+"个");
		return vCode;
	}

	@Override
	public void removeValidateCode(ValidateCode validateCode) {
		if (pool != null) {
			pool.remove(validateCode);
			cache.set(POOL_KEY, pool);
		}

	}

	@Override
	public void flushAll() {
		List<ValidateCode>  temp_pool = (List<ValidateCode>) cache
				.get(POOL_KEY);
		ValidateCode vCode = null;
		boolean flag=false;
		if (temp_pool != null) {
			for (ValidateCode validateCode: pool) {
				if ((new Date().getTime() -validateCode.getTime()
						.getTime()) > OVERTIME) {
					
					removeValidateCode(validateCode);
					break;
				}
			}

		}
	}

	private void updatePool(List<ValidateCode> temp_pool) {
		if(temp_pool!=null){
			cache.set(POOL_KEY, temp_pool);
		}
		
	}

	@Override
	public int fillPool() {
		int size=getAvailableNum();
		ValidateCode validateCode =null;
		if(size<max_size){
			for(int i = size;i<=(size+per_size)&&i<=max_size;i++){

//				validateCode=webLogin.getValidateCodeImg();
				pool.add(validateCode);
				updatePool(pool);
			}	
		}
			
		size=getAvailableNum();
		
		return size;
	}

}
