package com.ets.nb_iot.cmdinfo.command.send;

import com.ets.business.commandsendlog.entity.nb_command_send_log;
import com.ets.business.commandsendlog.service.CommandSendLogService;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.common.DateTimeUtils;
import com.ets.nb_iot.cmdinfo.iotinit.IntiClient;
import com.ets.nb_iot.cmdinfo.iotinit.NbIotConfig;
import com.ets.nb_iot.model.ReportDataHAC;
import com.ets.nb_iot.model.ReportDataTLV;
import com.ets.system.cache.RedisClientTemplate;
import com.ets.system.sysCommandSendLog.entity.tb_sys_command_send_log;
import com.ets.system.sysCommandSendLog.service.SysCommandSendLogService;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysEquipment.service.SysEquipmentService;
import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.CommandDTOV4;
import com.iotplatform.client.dto.PostDeviceCommandInDTO2;
import com.iotplatform.client.dto.PostDeviceCommandOutDTO2;
import com.iotplatform.client.invokeapi.SignalDelivery;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class CommandService {

	private static Logger logger = LoggerFactory.getLogger(CommandService.class);

	@Resource
	EquipmentService equipmentService;

	@Resource
    SysEquipmentService sysEquipmentService;

	@Autowired
    NbIotConfig nbIotConfig;

	@Autowired
    IntiClient initClient;

	@Autowired
    RedisClientTemplate redisClientTemplate;

	@Autowired
	CommandSendLogService commandSendLogService;

	@Resource
    SysCommandSendLogService sysCommandSendLogService;

	NorthApiClient northApiClient ;
	SignalDelivery signalDelivery ;
	String accessToken ;

	private void init()
	{
		northApiClient = initClient.GetNorthApiClient();
		signalDelivery = new SignalDelivery(northApiClient);
		accessToken = initClient.getAccessToken();
	}


	/**
	 * 
	* @Title: postCommandTLV 
	* @Description: 命令下发
	* @param: @param signalDelivery 传递对象
	* @param: @param deviceId 设备ID
	* @param: @param accessToken 令牌
	* @param: @param cmd 命令对象
	* @param: @param method 命令方法  
	* @return: PostDeviceCommandOutDTO2    
	* @Date: 2019年7月25日 下午6:13:53  
	* @throws
	 */
	public PostDeviceCommandOutDTO2 postCommandTLV(SignalDelivery signalDelivery, String deviceId, String accessToken,CommandDTOV4 cmd,String method) {
		try {
			PostDeviceCommandInDTO2 pdcInDTO = new PostDeviceCommandInDTO2();
			pdcInDTO.setDeviceId(deviceId);
			pdcInDTO.setExpireTime(0); //立即下发
			pdcInDTO.setMaxRetransmit(3); //最大重传次数
			pdcInDTO.setCommand(cmd);
			if(method.equals("SETRAW")){
				pdcInDTO.setCallbackUrl(nbIotConfig.getCallback_tlv_basic_url());
			}

			return signalDelivery.postDeviceCommand(pdcInDTO, null, accessToken);
		} catch (NorthApiException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 
	* @Title: postCommand 
	* @Description: 命令下发
	* @param: @param signalDelivery 传递对象
	* @param: @param deviceId 设备ID
	* @param: @param accessToken 令牌
	* @param: @param cmd 命令对象
	* @param: @param method 命令方法
	* @return: PostDeviceCommandOutDTO2    
	* @Date: 2019年7月25日 下午6:12:21  
	 */
	public PostDeviceCommandOutDTO2 postCommand(SignalDelivery signalDelivery, String deviceId, String accessToken,CommandDTOV4 cmd,String method) {
		try {
			PostDeviceCommandInDTO2 pdcInDTO = new PostDeviceCommandInDTO2();
			pdcInDTO.setDeviceId(deviceId);
			pdcInDTO.setExpireTime(0); //立即下发
			pdcInDTO.setMaxRetransmit(3); //最大重传次数
			pdcInDTO.setCommand(cmd);
			if(method.equals(nbIotConfig.getValve_control())){
				pdcInDTO.setCallbackUrl(nbIotConfig.getCallback_valve_control_url());
			}else if(method.equals(nbIotConfig.getWater_meter_basic())){
				pdcInDTO.setCallbackUrl(nbIotConfig.getCallback_water_meter_basic_url());
			}else if(method.equals(nbIotConfig.getDelivery())){
				pdcInDTO.setCallbackUrl(nbIotConfig.getCallback_delivery_url());
			}else if(method.equals(nbIotConfig.getCheck_time())){
				pdcInDTO.setCallbackUrl(nbIotConfig.getCallback_check_time_url());
			}else{
				pdcInDTO.setCallbackUrl(null);
			}

			return signalDelivery.postDeviceCommand(pdcInDTO, null, accessToken);
		} catch (NorthApiException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	* @Title: CheckTime 
	* @Description: 校时命令,并发送命令
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体       
	* @return: void    
	* @Date: 2019年7月25日 下午6:11:58  
	* @throws
	 */
	public void CheckTime(String deviceId, ReportDataHAC reportData){

		try {
			if(reportData != null){

				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId(nbIotConfig.getService_name());
				cmd.setMethod(nbIotConfig.getCheck_time()); //"PUT" is the command name defined in the profile
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);
				postCommand(signalDelivery, deviceId, accessToken,cmd,nbIotConfig.getCheck_time());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: AssemblyValveControlCommand 
	* @Description: 阀控命令,并发送命令
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体     
	* @return: void    
	* @Date: 2019年7月25日 下午6:11:21  
	* @throws
	 */
	public void AssemblyValveControlCommand(String deviceId, ReportDataHAC reportData){

		try {

			nb_watermeter_equipment e = equipmentService.queryWMEinfoByDeviceId(deviceId);

			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(reportData != null){
				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId(nbIotConfig.getService_name());
				cmd.setMethod(nbIotConfig.getValve_control()); //"PUT" is the command name defined in the profile
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);

				PostDeviceCommandOutDTO2 pdcOutDTO = postCommand(signalDelivery, deviceId, accessToken,cmd,nbIotConfig.getValve_control());
				if (pdcOutDTO != null) {

					logger.info("pdcOutDTO:========AssemblyValveControlCommand==========:"+pdcOutDTO.toString());
					
					String commandId = pdcOutDTO.getCommandId();

					if(e != null){
						nb_command_send_log cmdlog = new nb_command_send_log();
						cmdlog.setCustomercode(e.getCustomercode());
						cmdlog.setDeviceid(deviceId);
						cmdlog.setImei(e.getWatermetercode());
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setStatus(pdcOutDTO.getStatus());
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						commandSendLogService.addCommandSendLog(cmdlog);
					}

					if(sysEquipment != null){
						tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
						cmdlog.setDeviceid(deviceId);
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setImei(sysEquipment.getImei());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setStatus(pdcOutDTO.getStatus());
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						sysCommandSendLogService.addSysCommandSendLog(cmdlog);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: AssemblyDeliveryCommand 
	* @Description: 上报周期命令,并发送命令
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体   
	* @return: void    
	* @Date: 2019年7月25日 下午6:08:11  
	* @throws
	 */
	public void AssemblyDeliveryCommand(String deviceId, ReportDataHAC reportData){
		try {


			nb_watermeter_equipment e = equipmentService.queryWMEinfoByDeviceId(deviceId);
			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(reportData != null){
				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId(nbIotConfig.getService_name());
				cmd.setMethod(nbIotConfig.getDelivery()); 
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);


				PostDeviceCommandOutDTO2 pdcOutDTO = postCommand(signalDelivery, deviceId, accessToken,cmd,nbIotConfig.getDelivery());
				if (pdcOutDTO != null) {
					
					logger.info("pdcOutDTO:========AssemblyDeliveryCommand==========:"+pdcOutDTO.toString());

					String commandId = pdcOutDTO.getCommandId();

					if(e != null){
						nb_command_send_log cmdlog = new nb_command_send_log();
						cmdlog.setCustomercode(e.getCustomercode());
						cmdlog.setImei(e.getWatermetercode());
						cmdlog.setDeviceid(deviceId);
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setStatus(pdcOutDTO.getStatus());
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						commandSendLogService.addCommandSendLog(cmdlog);
					}

					if(sysEquipment != null){
						tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
						cmdlog.setDeviceid(deviceId);
						cmdlog.setImei(sysEquipment.getImei());
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setStatus(pdcOutDTO.getStatus());
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						sysCommandSendLogService.addSysCommandSendLog(cmdlog);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: AssemblyWaterMeterBasicCommand 
	* @Description: 表读数命令,并发送命令
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体     
	* @return: void    
	* @Date: 2019年7月25日 下午6:09:09  
	* @throws
	 */
	public void AssemblyWaterMeterBasicCommand(String deviceId, ReportDataHAC reportData){
		try {

			nb_watermeter_equipment e = equipmentService.queryWMEinfoByDeviceId(deviceId);
			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(reportData != null){
				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId(nbIotConfig.getService_name());
				cmd.setMethod(nbIotConfig.getWater_meter_basic()); //"PUT" is the command name defined in the profile
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);

				PostDeviceCommandOutDTO2 pdcOutDTO = postCommand(signalDelivery, deviceId, accessToken,cmd,nbIotConfig.getWater_meter_basic());

				if (pdcOutDTO != null) {
					
					logger.info("pdcOutDTO:========AssemblyWaterMeterBasicCommand==========:"+pdcOutDTO.toString());

					String commandId = pdcOutDTO.getCommandId();

					if(e != null){
						nb_command_send_log cmdlog = new nb_command_send_log();
						cmdlog.setImei(e.getWatermetercode());
						cmdlog.setCustomercode(e.getCustomercode());
						cmdlog.setDeviceid(deviceId);
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setStatus(pdcOutDTO.getStatus());
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						commandSendLogService.addCommandSendLog(cmdlog);
					}

					if(sysEquipment != null){
						tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
						cmdlog.setDeviceid(deviceId);
						cmdlog.setImei(sysEquipment.getImei());
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						cmdlog.setStatus(pdcOutDTO.getStatus());
						sysCommandSendLogService.addSysCommandSendLog(cmdlog);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	* @Title: AssemblyTLVBasicCommand 
	* @Description: 透传协议设置表读数，命令发送
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体  
	* @return: void    
	* @Date: 2019年7月25日 下午6:09:39  
	* @throws
	 */
	public void AssemblyTLVBasicCommand(String deviceId, ReportDataTLV reportData) {

		try {

			nb_watermeter_equipment e = equipmentService.queryWMEinfoByDeviceId(deviceId);

			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(reportData != null){
				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId("Reading");
				cmd.setMethod("SETRAW"); //"PUT" is the command name defined in the profile
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);

				PostDeviceCommandOutDTO2 pdcOutDTO = postCommandTLV(signalDelivery, deviceId, accessToken,cmd,"SETRAW");

				if (pdcOutDTO != null) {
					
					logger.info("pdcOutDTO:========AssemblyTLVBasicCommand==========:"+pdcOutDTO.toString());

					String commandId = pdcOutDTO.getCommandId();

					if(e != null){
						nb_command_send_log cmdlog = new nb_command_send_log();
						cmdlog.setImei(e.getWatermetercode());
						cmdlog.setCustomercode(e.getCustomercode());
						cmdlog.setDeviceid(deviceId);
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						cmdlog.setStatus(pdcOutDTO.getStatus());
						commandSendLogService.addCommandSendLog(cmdlog);
					}

					if(sysEquipment != null){
						tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
						cmdlog.setDeviceid(deviceId);
						cmdlog.setImei(sysEquipment.getImei());
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						cmdlog.setStatus(pdcOutDTO.getStatus());
						sysCommandSendLogService.addSysCommandSendLog(cmdlog);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	* @Title: CheckTimeTLV 
	* @Description: 透传协议校时命令发送
	* @param: @param deviceId 设备ID
	* @param: @param reportData 命令体  
	* @return: void    
	* @Date: 2019年7月25日 下午6:10:18  
	 */
	public void CheckTimeTLV(String deviceId, ReportDataTLV reportData) {

		try {

			nb_watermeter_equipment e = equipmentService.queryWMEinfoByDeviceId(deviceId);

			tb_sys_equipment sysEquipment = sysEquipmentService.querySysWMEinfoByDeviceId(deviceId);

			if(reportData != null){

				init();
				CommandDTOV4 cmd = new CommandDTOV4();
				cmd.setServiceId(nbIotConfig.getService_name());
				cmd.setMethod(nbIotConfig.getCheck_time()); //"PUT" is the command name defined in the profile
				Map<String, Object> cmdParam = new HashMap<String, Object>();
				cmdParam.put(nbIotConfig.getAttribute_name(), reportData);
				cmd.setParas(cmdParam);

				PostDeviceCommandOutDTO2 pdcOutDTO = postCommandTLV(signalDelivery, deviceId, accessToken,cmd,nbIotConfig.getCheck_time());

				if (pdcOutDTO != null) {
					
					logger.info("pdcOutDTO:========CheckTimeTLV==========:"+pdcOutDTO.toString());

					String commandId = pdcOutDTO.getCommandId();

					if(e != null){
						nb_command_send_log cmdlog = new nb_command_send_log();
						cmdlog.setImei(e.getWatermetercode());
						cmdlog.setCustomercode(e.getCustomercode());
						cmdlog.setDeviceid(deviceId);
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						cmdlog.setStatus(pdcOutDTO.getStatus());
						commandSendLogService.addCommandSendLog(cmdlog);
					}

					if(sysEquipment != null){
						tb_sys_command_send_log cmdlog = new tb_sys_command_send_log();
						cmdlog.setDeviceid(deviceId);
						cmdlog.setImei(sysEquipment.getImei());
						cmdlog.setSendtime(DateTimeUtils.getnowdate());
						cmdlog.setCommandid(commandId);
						cmdlog.setCommand(JSONObject.fromObject(pdcOutDTO.getCommand()).toString());
						cmdlog.setSendtime(DateTimeUtils.getTime(pdcOutDTO.getPlatformIssuedTime()));
						cmdlog.setExecutetime(pdcOutDTO.getExecuteTime());
						cmdlog.setStatus(pdcOutDTO.getStatus());
						sysCommandSendLogService.addSysCommandSendLog(cmdlog);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
