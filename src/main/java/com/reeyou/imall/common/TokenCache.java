package com.reeyou.imall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author Reeyou
 * @data 2019/5/17 10:24
 */
public class TokenCache {

	private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

	//LRU算法
	private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder()
			.initialCapacity(1000)
			.maximumSize(10000)
			.expireAfterAccess(12, TimeUnit.HOURS)
			.build(new CacheLoader<String, String>() {
				//默认数据加载实现，当调用方法取值时，如果key没有对应的值，就调用这个方法进行加载；
				@Override
				public String load(String key) throws Exception {
					return "null";
				}
			});

	public static void setKey(String key, String value) {
		localCache.put(key,value);
	}

	public static String getKey(String key) {
		String value = null;
		try {
			value = localCache.get(key);
			if("null".equals(value)) {
				return null;
			}
			return value;
		} catch (Exception e) {
			logger.error("localCache get Error", e);
		}
		return null;
	}

}
