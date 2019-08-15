package com.ets.system.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * @author: 姚轶文
 * @date:2018年10月25日 下午2:56:25
 * @version :
 * 
 */
@Service
public class RedisCache<K,V> implements Cache<K,V>{

	@Autowired
	public RedisClientTemplate redisClientTemplate;
	
	 public String getKeyPrefix() {
	        return keyPrefix;
	    }

	 public void setKeyPrefix(String keyPrefix) {
	        this.keyPrefix = keyPrefix;
	    }

	  private String keyPrefix = "shiro_redis_session:";
	    /**
	     * 获得byte[]型的key
	     * @param key
	     * @return
	     */
	    private byte[] getByteKey(Object key){
	        if(key instanceof String){
	            String preKey = this.keyPrefix + key;
	            return preKey.getBytes();
	        }else{
				ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
	            return byteSourceUtils.serialize((Serializable) key);
	        }
	    }


	    @Override
	    public Object get(Object key) throws CacheException {

	        byte[] bytes = getByteKey(key);
	        byte[] value = redisClientTemplate.get(bytes);
	        if(value == null){
	            return null;
	        }
			ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
	        return byteSourceUtils.deserialize(value);
	    }

	    /**
	     * 将shiro的缓存保存到redis中
	     */
	    @Override
	    public Object put(Object key, Object value) throws CacheException {

	        //Jedis jedis = jedisClientSingle.getJedis();
			ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
			redisClientTemplate.set(getByteKey(key), byteSourceUtils.serialize((Serializable)value));
	        byte[] bytes = redisClientTemplate.get(getByteKey(key));
	        Object object = byteSourceUtils.deserialize(bytes);
			redisClientTemplate.expire(getByteKey(key),3600*6);
	        return object;

	    }

	    @Override
	    public Object remove(Object key) throws CacheException {
	        //Jedis jedis = jedisClientSingle.getJedis();

	        byte[] bytes = redisClientTemplate.get(getByteKey(key));

			redisClientTemplate.del(getByteKey(key));
			ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
	        return byteSourceUtils.deserialize(bytes);
	    }

	    /**
	     * 清空所有缓存
	     */
	    @Override
	    public void clear() throws CacheException {
			redisClientTemplate.clear();
	    }

	    /**
	     * 缓存的个数
	     */
	    @Override
	    public int size() {
	        Long size = redisClientTemplate.getSize();
	        return size.intValue();
	    }

	    /**
	     * 获取所有的key
	     */
	    @Override
	    public Set keys() {
	        Set<byte[]> keys = redisClientTemplate.getSet(new String("*").getBytes());
	        Set<Object> set = new HashSet<Object>();
			ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
	        for (byte[] bs : keys) {
	            set.add(byteSourceUtils.deserialize(bs));
	        }
	        return set;
	    }


	    /**
	     * 获取所有的value
	     */
	    @Override
	    public Collection values() {
	        Set keys = this.keys();

	        List<Object> values = new ArrayList<Object>();
			ByteSourceUtils byteSourceUtils =new ByteSourceUtils();
	        for (Object key : keys) {
	            byte[] bytes = redisClientTemplate.get(getByteKey(key));
	            values.add(byteSourceUtils.deserialize(bytes));
	        }
	        return values;
	    }

}
