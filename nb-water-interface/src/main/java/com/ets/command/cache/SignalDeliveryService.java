package com.ets.command.cache;

import com.ets.command.body.CommandBody;
import com.ets.command.body.CommandBodyTLV;
import com.ets.system.redis.ByteSourceUtils;
import com.ets.system.redis.RedisClientTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 姚轶文
 * @create 2018- 11-27 11:17
 */
@Service
public class SignalDeliveryService {

	@Autowired
    RedisClientTemplate redisClientTemplate;


	/**
	 * 讲命令序列化存入到redies内
	 * @param jedisKey 存在人redis内的key值
	 * @param object 命令
	 * @return
	 */
	private void setJedisDate(String type,String jedisKey,Object object){

		ByteSourceUtils byteSourceUtils = new ByteSourceUtils();
		byte[] bytes = byteSourceUtils.serialize(object);

		//Jedis jedis = jedisClientSingle.getJedis();
		String keyPrefix = "redis_command_" + type+ ":" + jedisKey;
		redisClientTemplate.set(keyPrefix.getBytes(),bytes);

	}
	

	public void optionWMJedis(String deviceId,String type,int value) throws Exception {

		try {
			String jedisKey = deviceId + type;
			
			Object object = new CommandBody().getValveControlCmd(value);
			
			setJedisDate(type,jedisKey, object);
			
			setJedisDate("count_"+type,jedisKey, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void reportCycleJedis(String deviceId, String time,String type) {
		try {
			String jedisKey = deviceId + type;

			Object object = new CommandBody().getDeliveryCmd(time);
			
			setJedisDate(type,jedisKey, object);
			
			setJedisDate("count_"+type,jedisKey, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void readBasicNumHACJedis(String deviceId, String basenum,String type) {
		try {
			String jedisKey = deviceId + type;

			Object object = new CommandBody().getWaterMeterBasicCmd(basenum);
			
			setJedisDate(type,jedisKey, object);
			
			setJedisDate("count_"+type,jedisKey, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void readBasicNumTLVJedis(String deviceId, String basenum,String type) {
		try {
			String jedisKey = deviceId + type;

			Object object = new CommandBodyTLV().getReportDataTLVCmd(basenum);
			
			setJedisDate(type,jedisKey, object);
			
			setJedisDate("count_"+type,jedisKey, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
