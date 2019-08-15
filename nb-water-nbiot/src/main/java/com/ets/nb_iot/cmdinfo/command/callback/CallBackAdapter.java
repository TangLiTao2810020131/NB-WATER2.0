package com.ets.nb_iot.cmdinfo.command.callback;


import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.business.commandsendlog.service.CommandSendLogService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.remote.service.RemoteService;
import com.ets.common.JsonUtils;
import com.ets.common.ObjectCode;
import com.ets.nb_iot.body.ObjectClass;
import com.ets.nb_iot.cmdinfo.iotinit.NbIotConfig;
import com.ets.nb_iot.model.CallBackObject;
import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ValveControl;
import com.ets.system.cache.ByteSourceUtils;
import com.ets.system.cache.RedisClientTemplate;
import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;
import com.ets.system.sysCommandSendLog.service.SysCommandSendLogService;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysEquipment.service.SysEquipmentService;
import com.ets.system.sysdaelaytime.service.SysDelayTimeService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(value = "singleton")
public class CallBackAdapter implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(CallBackAdapter.class);

	CallBackDelay callBackDelay;

	@Autowired
    NbIotConfig nbIotConfig;
	
	@Autowired
    RemoteService remoteService;

	@Resource
	EquipmentService equipmentService;

	@Resource
    SysEquipmentService sysEquipmentService;

	@Resource
    SysDelayTimeService sysDelayTimeService;

	@Resource
    RedisClientTemplate redisClientTemplate;

	@Resource
    CommandSendLogService commandSendLogService;

	@Resource
    SysCommandSendLogService sysCommandSendLogService;


	@Override
	public void run() {

		logger.info("根据命令执行状态，进行操作，若未成功则重发命令");

		if(callBackDelay != null){

			String commandId = callBackDelay.getCommandId();
			String deviceId = callBackDelay.getDeviceId();
			String type = callBackDelay.getType();

			String jedisKey = deviceId + type;//拼接Redis内设备命令key

			String keyPrefix = "redis_command_" + type + ":" + jedisKey;//拼接Redis内设备命令区域key

			String keyPrefixCount = "redis_command_count_" + type + ":" + jedisKey;//拼接Redis内设备命令下发重复次数区域key

			nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(deviceId);

			if(equipment != null){

				Map<String,Object> map = new HashMap<String,Object>();
				map.put("commandid", commandId);
				nb_command_send_log log = commandSendLogService.info(map);

				if(ObjectCode.NB_SUCCESSFUL.equals(log.getStatus())){

					logger.info("客户端命令下发成功删除命令" + type);

					redisClientTemplate.del(keyPrefix);//删除命令

					redisClientTemplate.del(keyPrefixCount);//删除命令重复次数
					
					if(nbIotConfig.getValve_control().equals(type)){
						//更新阀控状态
						CallBackObject object = new JsonUtils().JsonToCallbackObject(log.getRcommand());
						if(object != null){

							optinControl(object);//更新阀控状态
						}
					}else{
						
						repeatCommand(type,keyPrefix,keyPrefixCount);//重发命令
					}
				}
			}

			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(sysEquipment != null){

				Map<String,Object> map = new HashMap<String,Object>();
				map.put("commandid", commandId);
				tb_sys_command_send_log log = sysCommandSendLogService.info(map);

				if(ObjectCode.NB_SUCCESSFUL.equals(log.getStatus())){

					logger.info(type + "命令重复下发成功删除命令");

					redisClientTemplate.del(keyPrefix);//删除命令

					redisClientTemplate.del(keyPrefixCount);//删除命令重复次数

					if(nbIotConfig.getValve_control().equals(type)){
						//更新阀控状态
						CallBackObject object = new JsonUtils().JsonToCallbackObject(log.getRcommand());
						if(object != null){

							optinSysControl(object);
						}
					}

				}else{
					
					repeatCommand(type,keyPrefix,keyPrefixCount);
				}
			}
		}
	}
	
	/**
	 * 
	* @Title: repeatCommand 
	* @Description: 最多三次 重复发送命令
	* @param: @param type 命令类型
	* @param: @param keyPrefix Redis 命令KEY键
	* @param: @param keyPrefixCount  Redis 重发次数KEY键
	* @return: void    
	* @Date: 2019年7月25日 下午5:48:44  
	 */
	private void repeatCommand(String type,String keyPrefix,String keyPrefixCount){

		ByteSourceUtils byteSourceUtils = new ByteSourceUtils();

		byte[] bytesCount = redisClientTemplate.get(keyPrefixCount.getBytes());//获取Redis内的下发重复次数值

		if(bytesCount != null){

			int count = (int) byteSourceUtils.deserialize(bytesCount);//转化为整型

			if(count <= 2){//若命令重复下发次数小于2则继续下发命令

				count += 1;//命令重复次数累加

				redisClientTemplate.set(keyPrefixCount.getBytes(),byteSourceUtils.serialize(count));//更新Redis内的命令重复次数

				logger.info(type + "命令重复下发次数：" + count);

			}else{

				logger.info(type + "命令重复下发次数超过：" + count + "删除命令");
				redisClientTemplate.del(keyPrefix);//删除命令
				redisClientTemplate.del(keyPrefixCount);//删除命令重复次数
			}
		}else{

			logger.info(type + "命令重复下发次数为空则删除命令或计数");
			redisClientTemplate.del(keyPrefix);//删除命令
			redisClientTemplate.del(keyPrefixCount);//删除命令重复次数

		}
	
	}
	
	
	/**
	 * 
	* @Title: optinControl 
	* @Description: 客户端阀控状态更新
	* @param: @param object 响应命令实体  
	* @Date: 2019年7月25日 下午5:48:00  
	 */
	private void optinControl(CallBackObject object ){
		
		ObjectClass o = new ObjectClass();

		JSONObject json1 = JSONObject.fromObject(object.getResult());
		JSONObject resultDetailJson = JSONObject.fromObject(json1.get("resultDetail"));
		JSONObject reportDataJson = JSONObject.fromObject(resultDetailJson.get("reportData"));
		JSONObject serviceDataJson = JSONObject.fromObject(reportDataJson.get("serviceData"));
		JSONObject jsonObject = JSONObject.fromObject(serviceDataJson.get("reportData"));
		ReportDataHAC rdata = (ReportDataHAC)JSONObject.toBean(jsonObject, ReportDataHAC.class);

		Map<String,Object>[] devmap = rdata.getDev();
		
		for(int i = 0;i< devmap.length;i++){

			Map<String,Object> mapd = (devmap[i]);

			String bn = (mapd.get("bn")).toString();

			if(bn.equals(ObjectCode.VALVE_CONTROL_CODE)){

				ValveControl value = o.MapToValue(mapd);

				logger.info("客户端更新阀门状态：" + value.toString());

				if("0".equals(value.getValveCurrentStatus())){

					remoteService.open(object.getDeviceId());
				}
				if("1".equals(value.getValveCurrentStatus())){

					remoteService.close(object.getDeviceId());
				}
			}
		}

	}

	/**
	 * 
	* @Title: optinSysControl 
	* @Description: 系统端阀控更新
	* @param: @param object  响应命令实体 
	* @return: void    
	* @Date: 2019年7月25日 下午5:52:00  
	 */
	private void optinSysControl(CallBackObject object ){
		
		ObjectClass o = new ObjectClass();

		JSONObject json1 = JSONObject.fromObject(object.getResult());
		JSONObject resultDetailJson = JSONObject.fromObject(json1.get("resultDetail"));
		JSONObject reportDataJson = JSONObject.fromObject(resultDetailJson.get("reportData"));
		JSONObject serviceDataJson = JSONObject.fromObject(reportDataJson.get("serviceData"));
		JSONObject jsonObject = JSONObject.fromObject(serviceDataJson.get("reportData"));
		ReportDataHAC rdata = (ReportDataHAC)JSONObject.toBean(jsonObject, ReportDataHAC.class);

		Map<String,Object>[] devmap = rdata.getDev();

		for(int i = 0;i< devmap.length;i++){

			Map<String,Object> mapd = (devmap[i]);

			String bn = (mapd.get("bn")).toString();

			if(bn.equals(ObjectCode.VALVE_CONTROL_CODE)){

				ValveControl value = o.MapToValue(mapd);

				logger.info("系统更新阀门状态：" + value.toString());

				if("0".equals(value.getValveCurrentStatus())){

					sysEquipmentService.open(object.getDeviceId());
				}
				if("1".equals(value.getValveCurrentStatus())){

					sysEquipmentService.close(object.getDeviceId());
				}
			}
		}

	}

	public CallBackDelay getCallBackDelay() {
		return callBackDelay;
	}

	public void setCallBackDelay(CallBackDelay callBackDelay) {
		this.callBackDelay = callBackDelay;
	}
}