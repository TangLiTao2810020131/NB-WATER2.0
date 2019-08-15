package com.ets.api.controller;
 
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.remote.entity.DeviceInfoEntity;
import com.ets.common.Message;
import com.ets.nb_iot.cmdinfo.command.accept.DeviceDataChange;
import com.ets.nb_iot.cmdinfo.iotinit.DataCollectionService;
import com.ets.nb_iot.cmdinfo.iotinit.DeviceManagementService;
import com.ets.nb_iot.cmdinfo.iotinit.IntiClient;
import com.ets.nb_iot.cmdinfo.iotinit.NbIotConfig;
import com.ets.nb_iot.cmdinfo.iotinit.SignalDeliveryService;
import com.ets.system.cache.RedisClientTemplate;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.google.gson.Gson;
import com.iotplatform.client.dto.DeviceInfo;
import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.dto.QuerySingleDeviceInfoOutDTO;
import com.iotplatform.client.dto.RegDirectDeviceOutDTO;
import com.iotplatform.client.invokeapi.DeviceManagement;
 

/**
 * 
 * @ClassName:     ApiController.java 
 * @Description:   nb-iot接口
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午5:01:44
 */
@RestController
@RequestMapping("/devicedatachange")
public class ApiController {
	
	@Autowired
	DeviceDataChange deviceDataChange;
	
	@Autowired
    NbIotConfig nbIotConfig;
	
	@Autowired
	IntiClient intiClient;
	
	@Autowired
    DeviceManagementService deviceManagementService;
    
	@Autowired
    DataCollectionService dataCollectionService;
	
	@Autowired
	SignalDeliveryService signalDeliveryService;
	
	@Autowired
    RedisClientTemplate redisClientTemplate;
	
	@RequestMapping("JsonToDeviceDataChange")
    public void JsonToDeviceDataChange(HttpServletRequest request){
        try{
        	deviceDataChange.JsonToDeviceDataChange(request);
        }catch(Exception e){
        }
    }
	
	
	/**
	 * 
	* @Title: OpentionEquipmentNBLOT 
	* @Description: 注册或修改NB平台设备 
	* @param: @param equipment水表设备详情
	* @return: String  设备ID  
	* @Date: 2019年7月25日 下午8:56:59  
	 */
	 @PostMapping(value="/OpentionSysEquipmentNBLOT")
	private String OpentionSysEquipmentNBLOT(@RequestBody tb_sys_equipment equipment){
		String deviceId = "";
		try {
			
			NorthApiClient cilent = intiClient.GetNorthApiClient();
			DeviceManagement deviceManagement = new DeviceManagement(cilent); //设备管理类
			if(equipment.getDeviceid() == null || "".equals(equipment.getDeviceid())){
				RegDirectDeviceOutDTO rddod = deviceManagementService.registerDevice(deviceManagement, 0,equipment.getImei());
				if(rddod != null){
					deviceId = rddod.getDeviceId();
				}
			}else{
				deviceId = equipment.getDeviceid();
			}
			if(!"".equals(deviceId)){
				deviceManagementService.modifyDeviceInfo(deviceManagement, deviceId, equipment.getId(),nbIotConfig.getDevice_type(),nbIotConfig.getManufacturer_id(),nbIotConfig.getManufacturer_name(),nbIotConfig.getModel(),nbIotConfig.getProtocol_type());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deviceId;
	}
	 
	 
		/**
		 * 
		* @Title: OpentionEquipmentNBLOT 
		* @Description: 注册或修改NB平台设备 
		* @param: @param equipment水表设备详情
		* @return: String  设备ID  
		* @Date: 2019年7月25日 下午8:56:59  
		 */
		 @PostMapping(value="/OpentionEquipmentNBLOT")
		private String OpentionEquipmentNBLOT(@RequestBody EquipmentModel equipment){
			String deviceId = "";
			try {
				
				NorthApiClient cilent = intiClient.GetNorthApiClient();
				DeviceManagement deviceManagement = new DeviceManagement(cilent); //设备管理类
				if(equipment.getDeviceid() == null || "".equals(equipment.getDeviceid())){
					RegDirectDeviceOutDTO rddod = deviceManagementService.registerDevice(deviceManagement, 0,equipment.getWatermetercode());
					if(rddod != null){
						deviceId = rddod.getDeviceId();
					}
				}else{
					deviceId = equipment.getDeviceid();
				}
				if(!"".equals(deviceId)){
					deviceManagementService.modifyDeviceInfo(deviceManagement, deviceId, equipment.getAddress(),nbIotConfig.getDevice_type(),nbIotConfig.getManufacturer_id(),nbIotConfig.getManufacturer_name(),nbIotConfig.getModel(),nbIotConfig.getProtocol_type());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return deviceId;
		}
	 
	 	/**
	 	 * 
	 	* @Title: DeleteEquipmentNBLOT 
	 	* @Description: 删除NB平台设备
	 	* @param: @param list
	 	* @return: String    
	 	* @Date: 2019年7月25日 下午8:57:36  
	 	 */
	 	@PostMapping(value="/DeleteEquipmentNBLOT")
		public String DeleteEquipmentNBLOT(@RequestBody List<String> list)
		{
			Gson gson = new Gson();
			try {
				
				NorthApiClient cilent = intiClient.GetNorthApiClient();
				DeviceManagement deviceManagement = new DeviceManagement(cilent); //设备管理类

				for (String deviceId : list) {
					deviceManagementService.deleteDirectDevice(deviceManagement, deviceId);
				}

				return gson.toJson(new Message("1","删除成功!"));
			} catch (Exception e) {
				e.printStackTrace();
				return gson.toJson(new Message("2","删除失败!"));
			}
		}
	 	
	 	/**
	 	 * 
	 	* @Title: DeviceInfo 
	 	* @Description: 查询设备信息
	 	* @param: @param deviceId 设备ID
	 	* @return: QuerySingleDeviceInfoOutDTO    
	 	* @Date: 2019年7月25日 下午9:30:19  
	 	 */
	 	@PostMapping(value="/DeviceInfo")
		public DeviceInfoEntity DeviceInfo(@RequestBody String deviceId)
		{
	 		DeviceInfoEntity entity = new DeviceInfoEntity();
			try {
				
				QuerySingleDeviceInfoOutDTO querySingleDeviceInfoOutDTO = dataCollectionService.querySingleDeviceInfo(deviceId,null);
				DeviceInfo deviceInfo = querySingleDeviceInfoOutDTO.getDeviceInfo();

				entity.setDeviceId(querySingleDeviceInfoOutDTO.getDeviceId());
				entity.setCreateTime(querySingleDeviceInfoOutDTO.getCreateTime());
				entity.setGatewayId(querySingleDeviceInfoOutDTO.getGatewayId());
				entity.setNodeType(querySingleDeviceInfoOutDTO.getNodeType());

				entity.setNodeId(deviceInfo.getNodeId());
				entity.setName(deviceInfo.getName());
				entity.setDescription(deviceInfo.getDescription());
				entity.setManufacturerId(deviceInfo.getManufacturerId());
				entity.setManufacturerName(deviceInfo.getManufacturerName());
				entity.setMac(deviceInfo.getMac());
				entity.setLocation(deviceInfo.getLocation());
				entity.setDeviceType(deviceInfo.getDeviceType());
				entity.setModel(deviceInfo.getModel());
				entity.setSwVersion(deviceInfo.getSwVersion());
				entity.setFwVersion(deviceInfo.getFwVersion());
				entity.setHwVersion(deviceInfo.getHwVersion());
				entity.setProtocolType(deviceInfo.getProtocolType());
				entity.setBridgeId(deviceInfo.getBridgeId());
				entity.setStatus(deviceInfo.getStatus());
				entity.setStatusDetail(deviceInfo.getStatusDetail());
				entity.setMute(deviceInfo.getMute());
				entity.setSupportedSecurity(deviceInfo.getSupportedSecurity());
				entity.setIsSecurity(deviceInfo.getIsSecurity());
				entity.setSignalStrength(deviceInfo.getSignalStrength());
				entity.setSigVersion(deviceInfo.getSigVersion());
				entity.setSerialNumber(deviceInfo.getSerialNumber());
				entity.setBatteryLevel(deviceInfo.getBatteryLevel());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return entity;
		}
	 	
	 	/**
	 	 * 
	 	* @Title: DeviceOpen 
	 	* @Description: 开水阀命令缓存
	 	* @param: @param id
	 	* @param: @return    
	 	* @return: boolean    
	 	* @Date: 2019年7月25日 下午9:34:42  
	 	* @throws
	 	 */
	 	@PostMapping(value="/DeviceOpen")
		public boolean DeviceOpen(@RequestBody String id[])
		{
			int value = 0;
			for(String deviceId : id)
			{
				try {
					signalDeliveryService.optionWMJedis(deviceId,nbIotConfig.getValve_control(),value);
					String times = String.valueOf(43200*3600);//开发后没12小时上报一次数据
					signalDeliveryService.reportCycleJedis(deviceId,times,nbIotConfig.getDelivery());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	 	
	 	/**
	 	 * 
	 	* @Title: DeviceClose 
	 	* @Description: 关闭水阀命令缓存
	 	* @param: @param id
	 	* @return: boolean    
	 	* @Date: 2019年7月25日 下午9:39:11  
	 	 */
	 	@PostMapping(value="/DeviceClose")
		public boolean DeviceClose(@RequestBody String id[])
		{
			int value = 1;
			for(String deviceId : id)
			{
				try {

					signalDeliveryService.optionWMJedis(deviceId,nbIotConfig.getValve_control(),value);
					String times = String.valueOf(15*3600);//关阀后每15分钟上报一次
					signalDeliveryService.reportCycleJedis(deviceId,times,nbIotConfig.getDelivery());
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	 	
	 	/**
	 	 * 
	 	* @Title: ReportCycleTime 
	 	* @Description: 上报周期命令缓存 
	 	* @param: @param map
	 	* @param: @return    
	 	* @return: boolean    
	 	* @Date: 2019年7月25日 下午9:44:32  
	 	* @throws
	 	 */
	    @PostMapping(value="/ReportCycleTime")
	    public boolean ReportCycleTime(@RequestBody Map<String,Object> map){
	        try{
	        	
	        	String times = (String) map.get("times");
	        	String id[] = (String[]) map.get("id");
	        	for (String deviceId : id) {
	        		signalDeliveryService.reportCycleJedis(deviceId,times,nbIotConfig.getDelivery());
				}
	        	return true;  
	        }catch(Exception e){
	            return false;
	        }
	    }
	    
	    /**
	     * 
	    * @Title: ReadBasicNum 
	    * @Description: 设置表读数下发命令缓存
	    * @param: @param map （id:设备ID集合，basenum表读数）
	    * @param: @return    
	    * @return: boolean    
	    * @Date: 2019年7月25日 下午9:47:24  
	    * @throws
	     */
	    @PostMapping(value="/ReadBasicNum")
		public boolean ReadBasicNum(@RequestBody Map<String,Object> map)
		{

			String id[] = (String[]) map.get("id");
			String basenum = (String) map.get("basenum");
			for(String deviceId : id)
			{
				try {
					String deviceid = deviceId.split("\\*")[0];
					String ismagnetism = deviceId.split("\\*")[1];

					if("1".equals(ismagnetism)){
						signalDeliveryService.readBasicNumHACJedis(deviceid,basenum,nbIotConfig.getWater_meter_basic());
					}
					if("0".equals(ismagnetism)){
						signalDeliveryService.readBasicNumTLVJedis(deviceid,basenum,"SETRAW");
					}
					return true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	    
	    /**
	     * 
	    * @Title: CleanCommand 
	    * @Description: 取消命令
	    * @param: @param map（id：设备ID集合，type：命令类型）
	    * @return: String    
	    * @Date: 2019年7月25日 下午9:51:24  
	     */
	    @PostMapping(value="/CleanCommand")
		public String CleanCommand(@RequestBody Map<String,Object> map)
		{

			String id[] = (String[]) map.get("id");
			String type[] = (String[]) map.get("type");
			Gson gson = new Gson();

			for(String deviceId : id)
			{
				try {
					
					for (String str : type) {
						String keyPrefix = "";
						String jedisKey = "";
						if(nbIotConfig.getDelivery().equals(str)){
							jedisKey = deviceId + "SETREPORTCYCLE";
							keyPrefix = "shiro_redis_command_" + nbIotConfig.getDelivery()+ ":" + jedisKey;
						}
						if(nbIotConfig.getWater_meter_basic().equals(str)){
							jedisKey = deviceId + "SETMETERREADING";
							keyPrefix = "shiro_redis_command_" + nbIotConfig.getWater_meter_basic()+ ":" + jedisKey;
						}
						if(nbIotConfig.getValve_control().equals(str)){
							jedisKey = deviceId + "VALVECONTROL";
							keyPrefix = "shiro_redis_command_" + nbIotConfig.getValve_control()+ ":" + jedisKey;
						}
						redisClientTemplate.del(keyPrefix);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return gson.toJson(new Message("0","取消下发命令失败!"));
				}
			}
			return gson.toJson(new Message("1","取消下发命令成功!"));

		}
}