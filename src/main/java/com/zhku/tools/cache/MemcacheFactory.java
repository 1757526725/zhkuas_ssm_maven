package com.zhku.tools.cache;

import com.zhku.tools.cache.impl.BaeMemcacheImpl;
import com.zhku.tools.cache.impl.LocalMemcacheImpl;
import com.zhku.tools.cache.impl.SaeMemcacheImpl;
import com.zhku.utils.WebConfigUtils;

public class MemcacheFactory {
	private static Memcache memcache = null;

	public static Memcache createMemcache() {
		if (memcache == null) {
			String web = WebConfigUtils.getValue("web_evim");
			if (web.equals("sae")) {
				memcache = new SaeMemcacheImpl();
			} else if (web.equals("bae")) {
				memcache = new BaeMemcacheImpl();
			} else {
				memcache = new LocalMemcacheImpl();
			}
		}
		return memcache;
	}
}
