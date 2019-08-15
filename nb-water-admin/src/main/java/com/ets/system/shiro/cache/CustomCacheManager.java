package com.ets.system.shiro.cache;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author: 姚轶文
 * @date:2018年10月25日 下午3:10:33
 * @version :
 * 
 */
public class CustomCacheManager implements CacheManager{

	@Autowired
	public RedisCache redisCache;
	
	@Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return redisCache;
    }
}
