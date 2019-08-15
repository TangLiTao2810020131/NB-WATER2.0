package com.ets.system.shiro.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author: 姚轶文
 * @date:2018年10月25日 下午3:00:05
 * @version :
 * 
 */

@Service
public class RedisClientTemplate {

    @Autowired
    private JedisDataSourceImpl redisDataSource;

    private static final Logger log = LoggerFactory.getLogger(RedisClientTemplate.class);

    public void disconnect() {
        Jedis jedis = redisDataSource.getRedisClient();
        jedis.disconnect();
    }


    /**
     * 设置单个值
     *
     * @param key
     * @param value
     * @return
     */
    public String set(String key, String value) {
        String result = null;

        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public void set(byte[] key , byte[] value)
    {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            jedis.set(key,value);
        }
        finally {
            redisDataSource.returnResource(jedis);
        }
    }

    /**
     * 获取单个值
     *
     * @param key
     * @return
     */
    public String get(String key) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = jedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public byte[] get(byte[] key) {
        byte[] result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }

        boolean broken = false;
        try {
            result = jedis.get(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public void del(byte[] key)
    {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            jedis.del(key);
        }
        finally {
            redisDataSource.returnResource(jedis);
        }

    }

    public void del(String key)
    {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            jedis.del(key);
        }
        finally {
            redisDataSource.returnResource(jedis);
        }
    }

    public Boolean exists(String key) {
        Boolean result = false;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.exists(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public String type(String key) {
        String result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.type(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    /**
     * 在某段时间后失效
     *
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public Long expire(byte[] key, int seconds) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.expire(key, seconds);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    /**
     * 在某个时间点失效
     *
     * @param key
     * @param unixTime
     * @return
     */
    public Long expireAt(String key, long unixTime) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.expireAt(key, unixTime);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public Long ttl(String key) {
        Long result = null;
        Jedis jedis = redisDataSource.getRedisClient();
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.ttl(key);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public boolean setbit(String key, long offset, boolean value) {

        Jedis jedis = redisDataSource.getRedisClient();
        boolean result = false;
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.setbit(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public boolean getbit(String key, long offset) {
        Jedis jedis = redisDataSource.getRedisClient();
        boolean result = false;
        if (jedis == null) {
            return result;
        }
        boolean broken = false;

        try {
            result = jedis.getbit(key, offset);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public long setRange(String key, long offset, String value) {
        Jedis jedis = redisDataSource.getRedisClient();
        long result = 0;
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.setrange(key, offset, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public String getRange(String key, long startOffset, long endOffset) {
        Jedis jedis = redisDataSource.getRedisClient();
        String result = null;
        if (jedis == null) {
            return result;
        }
        boolean broken = false;
        try {
            result = jedis.getrange(key, startOffset, endOffset);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            broken = true;
        } finally {
            redisDataSource.returnResource(jedis, broken);
        }
        return result;
    }

    public Set<byte[]> getSet(byte[] bytes)
    {
        Jedis jedis = redisDataSource.getRedisClient();
        Set<byte[]> keys = null;
        try {
        	keys = jedis.keys(bytes);
        }
        finally {
            redisDataSource.returnResource(jedis);
        }
        return keys;
    }

    public long getSize()
    {
        Jedis jedis = redisDataSource.getRedisClient();
        long dbSize = 0;
        try {
            dbSize = jedis.dbSize();
        }
        finally {
            redisDataSource.returnResource(jedis);
        }
        return dbSize;
    }

    public void clear()
    {
        Jedis jedis = redisDataSource.getRedisClient();
        try {
            jedis.flushDB();
        }
        finally {
            redisDataSource.returnResource(jedis);
        }
    }

    /**
     * 删除redis内所有的角色
     */
    public void delSession()
    {
    	try {
            String key = "shiro_redis_session:*";
            
            Set<byte[]> keys = getSet(key.getBytes());
            
            for (byte[] bs : keys) {
            	del(bs);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
