package com.zhku.tools.cache.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sina.sae.memcached.SaeMemcache;
import com.zhku.tools.cache.Memcache;

public class SaeMemcacheImpl implements Memcache {
	private SaeMemcache saeMemcache = new SaeMemcache();
	private Logger logger =Logger.getLogger(SaeMemcacheImpl.class.getName());
	@Override
	public Object get(String key) {
		saeMemcache.init();
		return saeMemcache.get(key);
	}

	@Override
	public boolean add(String key, Object value) {
		saeMemcache.init();
		boolean falg=saeMemcache.add(key, value);
		if(falg){
			 recordCacheKey(key);
		}else{
			logger.debug(key+"缓存失败");
		}
		return falg;
	}

	@Override
	public boolean set(String key, Object value) {
		saeMemcache.init();
		return saeMemcache.set(key, value);
	}

	@Override
	public void recordCacheKey(String key) {
		List<String> keys =(List<String>) this.get("Keys");
		if(keys==null){
			keys=new ArrayList<String>();
			keys.add(key);
			this.add("Keys", keys);
		}else{
			keys.add(key);
			this.set("Keys", keys);
		}
	}

	@Override
	public List<String> getCacheKeys() {
		return (List<String>) this.get("Keys");
	}

	@Override
	public void clearCache() {
		List<String> keys =getCacheKeys();
		if(keys!=null){
			for(String key:keys){
				delete(key);
			}
		}
	}

	@Override
	public boolean delete(String key) {
		saeMemcache.init();
		boolean falg=saeMemcache.delete(key);
		recordCacheKey(key);
		return falg;
	}

	@Override
	public void removeCacheKey(String key) {
		List<String> keys =getCacheKeys();
		keys.remove(key);
		this.set("Keys", keys);
	}

}
