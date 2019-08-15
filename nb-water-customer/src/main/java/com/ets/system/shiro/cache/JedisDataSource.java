package com.ets.system.shiro.cache;

import redis.clients.jedis.Jedis;

/**
 * @author 姚轶文
 * @create 2018- 12-17 15:55
 */
public interface JedisDataSource {
    Jedis getRedisClient();
    void returnResource(Jedis jedis);
    void returnResource(Jedis jedis, boolean broken);
}
