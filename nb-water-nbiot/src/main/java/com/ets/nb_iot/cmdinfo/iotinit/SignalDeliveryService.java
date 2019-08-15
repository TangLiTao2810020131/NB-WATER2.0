package com.ets.nb_iot.cmdinfo.iotinit;

import com.ets.nb_iot.body.CommandBody;
import com.ets.nb_iot.body.CommandBodyTLV;
import com.ets.system.cache.ByteSourceUtils;
import com.ets.system.cache.RedisClientTemplate;
import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.invokeapi.SignalDelivery;
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


	@Autowired
    IntiClient initClient;

	@Autowired
	DeviceManagementService deviceManagementService;

	NorthApiClient northApiClient ;
	SignalDelivery signalDelivery ;
	String accessToken ;

	public void init()
	{
		northApiClient = initClient.GetNorthApiClient();
		signalDelivery = new SignalDelivery(northApiClient);
		accessToken = initClient.getAccessToken();
	}

	
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
	

	public void optionWMJedis(String deviceId,String type,int value) throws NorthApiException {

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
