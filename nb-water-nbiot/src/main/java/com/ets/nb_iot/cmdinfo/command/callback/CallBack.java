package com.ets.nb_iot.cmdinfo.command.callback;

import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.business.commandsendlog.service.CommandSendLogService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.remote.service.RemoteService;
import com.ets.common.Common;
import com.ets.common.JsonUtils;
import com.ets.nb_iot.cmdinfo.iotinit.NbIotConfig;
import com.ets.nb_iot.model.CallBackObject;
import com.ets.system.cache.RedisClientTemplate;
import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;
import com.ets.system.sysCommandSendLog.service.SysCommandSendLogService;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysEquipment.service.SysEquipmentService;
import com.ets.system.sysdaelaytime.service.SysDelayTimeService;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CallBack {

	private static Logger logger = LoggerFactory.getLogger(CallBack.class);

	@Autowired
	RemoteService remoteService;

	@Autowired
	CommandSendLogService commandSendLogService;

	@Autowired
	SysCommandSendLogService sysCommandSendLogService;

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	SysEquipmentService sysEquipmentService;

	@Autowired
	NbIotConfig nbIotConfig;

	@Autowired
	RedisClientTemplate redisClientTemplate;

	@Autowired
	SysDelayTimeService sysDelayTimeService;

	/**
	 * 
	* @Title: ValveControlCallBackUrl 
	* @Description: 阀控命令回调函数
	* @param: @param request
	* @param: @throws Exception    
	* @return: String    
	* @Date: 2019年8月9日 下午5:04:20  
	* @throws
	 */
	public String ValveControlCallBackUrl(HttpServletRequest request) throws Exception{
		
		JsonUtils jsonUtils = new JsonUtils();
		
		logger.info("===============================start阀控命令回调函数============================================");
		
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		
		logger.info("回调方法" + date);
		
		CallBackObject  object = jsonUtils.JsonToCallbackObject(date);
		
		if(object != null){
			
			if(!"xxx".equals(object.getDeviceId())){
				
				nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(object.getDeviceId());

				tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(object.getDeviceId());

				if(equipment != null){
					
					String resultCode = object.getResult().get("resultCode");
					nb_command_send_log cmdlog = new nb_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					commandSendLogService.editCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),equipment.getDeviceid(),nbIotConfig.getValve_control(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}
				
				if(sysEquipment != null){
					
					sysEquipmentService.updateCstatus(sysEquipment.getDeviceid());
					
					String resultCode = object.getResult().get("resultCode");
					
					tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					sysCommandSendLogService.editSysCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),sysEquipment.getDeviceid(),nbIotConfig.getValve_control(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}

			}
		}
		logger.info("===============================end阀控命令回调函数===============================================");
		
		return "success";
	}

	/**
	 * 
	* @Title: WaterMeterBasicCallBackUrl 
	* @Description: 基础表数据命令回调函数
	* @param: @param request
	* @param: @throws Exception    
	* @return: String    
	* @Date: 2019年8月9日 下午5:04:42  
	* @throws
	 */
	public String WaterMeterBasicCallBackUrl(HttpServletRequest request) throws Exception{
		
		JsonUtils jsonUtils = new JsonUtils();
		
		logger.info("===============================start基础表数据命令回调函数============================================");
		
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		
		logger.info("回调方法" + date);
		
		CallBackObject  object = jsonUtils.JsonToCallbackObject(date);
		
		if(object != null){
			
			if(!"xxx".equals(object.getDeviceId())){
				
				nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(object.getDeviceId());

				tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(object.getDeviceId());
				
				String resultCode = object.getResult().get("resultCode");
				
				if(equipment != null){
					
					nb_command_send_log cmdlog = new nb_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					commandSendLogService.editCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),equipment.getDeviceid(),nbIotConfig.getWater_meter_basic(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}
				
				if(sysEquipment != null){
					
					tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					sysCommandSendLogService.editSysCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),sysEquipment.getDeviceid(),nbIotConfig.getWater_meter_basic(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);

				}

			}
		}		
		logger.info("===============================end基础表数据命令回调函数===============================================");
		return "success";
	}

	/**
	 * 
	* @Title: DeliveryCallBackUrl 
	* @Description: 上报周期命令回调函数
	* @param: @param request
	* @param: @throws Exception    
	* @return: String    
	* @Date: 2019年8月9日 下午5:05:02  
	* @throws
	 */
	public String DeliveryCallBackUrl(HttpServletRequest request) throws Exception{
		
		JsonUtils jsonUtils = new JsonUtils();
		
		logger.info("===============================start上报周期命令回调函数============================================");
		
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		
		logger.info("回调方法" + date);
		
		CallBackObject  object = jsonUtils.JsonToCallbackObject(date);
		if(object != null){
			
			if(!"xxx".equals(object.getDeviceId())){
				
				nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(object.getDeviceId());

				tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(object.getDeviceId());
				
				String resultCode = object.getResult().get("resultCode");
				
				if(equipment != null){
					
					nb_command_send_log cmdlog = new nb_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					commandSendLogService.editCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),equipment.getDeviceid(),nbIotConfig.getDelivery(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}
				
				if(sysEquipment != null){
					
					tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					sysCommandSendLogService.editSysCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),sysEquipment.getDeviceid(),nbIotConfig.getDelivery(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
					
				}
			}
			
		}			
		logger.info("===============================end上报周期命令回调函数===============================================");
		return "success";
	}

	/**
	 * 
	* @Title: CheckTimeCallBackUrl 
	* @Description: 校验时间命令回调函数
	* @param: @param request
	* @param: @throws Exception    
	* @return: String    
	* @Date: 2019年8月9日 下午5:05:17  
	* @throws
	 */
	public String CheckTimeCallBackUrl(HttpServletRequest request) throws Exception{
		//logger.info("===============================start校验时间命令回调函数============================================");
		//String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		//logger.info("回调方法"+date);
		//logger.info("===============================end校验时间命令回调函数===============================================");
		return "success";
	}

	/**
	 * 
	* @Title: WaterMeterTLVBasicCallBackUrl 
	* @Description: 设置表读数命令回调函数
	* @param: @param request
	* @param: @throws Exception    
	* @return: String    
	* @Date: 2019年8月9日 下午5:05:30  
	* @throws
	 */
	public String WaterMeterTLVBasicCallBackUrl(HttpServletRequest request) throws Exception{
		
		JsonUtils jsonUtils = new JsonUtils();
		
		logger.info("===============================start透传TVL命令回调函数============================================");
		
		String date = Common.getPostData(request.getInputStream(),request.getContentLength(),null);
		
		logger.info("回调方法" + date);
		
		CallBackObject  object = jsonUtils.JsonToCallbackObject(date);
		
		if(object != null){
			
			if(!"xxx".equals(object.getDeviceId())){
				
				nb_watermeter_equipment equipment = equipmentService.queryWMEinfoByDeviceId(object.getDeviceId());

				tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(object.getDeviceId());
				
				String resultCode = object.getResult().get("resultCode");
				
				
				if(equipment != null){
					
					nb_command_send_log cmdlog = new nb_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					commandSendLogService.editCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),equipment.getDeviceid(),nbIotConfig.getWater_meter_basic(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}
				
				if(sysEquipment != null){
					
					tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
					cmdlog.setRcommand(date);
					cmdlog.setStatus(resultCode);
					cmdlog.setCommandid(object.getCommandId());
					sysCommandSendLogService.editSysCommandSendLog(cmdlog);
					
					CallBackDelay callBackDelay = new CallBackDelay(object.getCommandId(),sysEquipment.getDeviceid(),nbIotConfig.getWater_meter_basic(),300000);
					CallBackQueue.getCallBackQueue().put(callBackDelay);
				}
			}
		}	
		logger.info("===============================end透传TVL命令回调函数===============================================");
		return "success";
	}


}
