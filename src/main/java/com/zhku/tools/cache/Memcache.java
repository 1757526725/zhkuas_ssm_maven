package com.zhku.tools.cache;

import java.util.List;

public interface Memcache {
	public  Object get(String key);
	public  boolean add(String key,Object value);
	public  boolean set(String key,Object value);
	public  void recordCacheKey(String key);
	public  void removeCacheKey(String key);
	public  List<String> getCacheKeys();
	public  void clearCache();
	public  boolean delete(String key);
}
