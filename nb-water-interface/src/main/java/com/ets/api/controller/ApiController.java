package com.ets.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ets.business.equipment.entity.WMControl;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.equipment.service.EquipmentService;
import com.ets.business.meterread.entity.nb_meterread;
import com.ets.business.meterread.service.MeterreadService;
import com.ets.business.meterreadlog.entity.nb_meterread_log;
import com.ets.business.meterreadlog.service.MeterreadlogService;
import com.ets.business.owner.entity.nb_owner;
import com.ets.business.owner.service.OwnerService;
import com.ets.command.cache.SignalDeliveryService;
import com.ets.common.Message;
import com.ets.common.NbIotConfig;
import com.ets.system.cus.entity.nb_cus;
import com.ets.system.cus.service.CusService;
import com.ets.system.watermeter.entity.nb_watermeter_dict;
import com.ets.system.watermeter.service.WatermeterService;
import net.sf.json.JSONObject;


/**
 * 
 * @ClassName:     ApiController.java 
 * @Description:   定时任务接口
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月24日 下午5:01:44
 */
@RestController
@RequestMapping("/api")
public class ApiController {

	@Resource
	SignalDeliveryService signalDeliveryService;

	@Resource
	NbIotConfig nbIotConfig;

	@Resource
	CusService cusService;

	@Resource
	OwnerService ownerService;

	@Resource
	EquipmentService equipmentService;

	@Resource
	MeterreadService meterreadService;

	@Resource
	MeterreadlogService meterreadlogService;

	@Resource
	WatermeterService watermeterService;

	/**
	 * 
	* @Title: QueryOwnerDetail 
	* @Description: 查询业主详情信息 
	* @param: @param ownerAccount 业主号
	* @param: @param custormerKey 客户秘钥
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:14:11  
	 */
	@GetMapping(value="/QueryOwnerDetail")
	public JSONObject QueryOwnerDetail(String ownerAccount,String custormerKey){

		Message m = new Message("10002 ","error","");
		//查询秘钥查询客户信息得到客户code
		nb_cus cus = cusService.queryCusByKey(custormerKey);
		//根据客户id和户号查询业主信息,若户号为空则查询全部业主信息
		String cusCode = cus.getId();
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("customerId",cusCode);
		mapPar.put("useraccount",ownerAccount);
		List<nb_owner> oList = ownerService.queryOwnerCusId(mapPar);

		if(oList.size() != 0){
			m = new Message("10000","success",oList);
		}else{
			m = new Message("10001","fail","");
		}

		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

	/**
	 * 
	* @Title: QueryWaterMeterDetail 
	* @Description: 查询水表详情信息
	* @param: @param imei 水表唯一号
	* @param: @param custormerKey 客户秘钥
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:15:16  
	 */
	@GetMapping(value="/QueryWaterMeterDetail")
	public JSONObject QueryWaterMeterDetail(String imei,String custormerKey){
		Message m = new Message("20002 ","error","");
		//查询秘钥查询客户信息得到客户code
		nb_cus cus = cusService.queryCusByKey(custormerKey);
		//根据客户id和户号查询水表信息,若户号为空则查询全部水表信息
		String cusCode = cus.getId();
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("customerId",cusCode);
		mapPar.put("watermetercode",imei);
		List<nb_watermeter_equipment> wList = equipmentService.queryWaterMeterCusId(mapPar);
		if(wList.size() != 0){
			m = new Message("20000","success",wList);
		}else{
			m = new Message("20001","fail","");
		}
		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

	/**
	 * 
	* @Title: QueryWMHistoryNum 
	* @Description: 查询水表最新表读数
	* @param: @param imei 水表唯一号
	* @param: @param custormerKey 客户秘钥
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:15:58  
	 */
	@GetMapping(value="/QueryWMHistoryNum")
	public JSONObject QueryWMHistoryNum(String imei,String custormerKey){
		Message m = new Message("30002 ","error","");
		//查询秘钥查询客户信息得到客户code
		nb_cus cus = cusService.queryCusByKey(custormerKey);
		//根据客户id和户号查询水表最新表读数,若户号为空则查询全部水表最新表读数
		String cusCode = cus.getId();
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("customerId",cusCode);
		mapPar.put("watermetercode",imei);
		List<nb_meterread> rList = meterreadService.queryWMHistoryNumCusId(mapPar);
		if(rList.size() != 0){
			m = new Message("30000","success",rList);
		}else{
			m = new Message("30001","fail","");
		}
		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

	/**
	 * 
	* @Title: QueryWMLatestNum 
	* @Description: 查询某个水表历史表读数
	* @param: @param imei 水表唯一号
	* @param: @param custormerKey 客户秘钥
	* @param: @param startDate 开始时间
	* @param: @param endDate 结束时间
	* @param: @return    
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:16:42  
	 */
	@GetMapping(value="/QueryWMLatestNum")
	public JSONObject QueryWMLatestNum(String imei,String custormerKey,String startDate,String endDate){
		Message m = new Message("40002","error","");
		//查询秘钥查询客户信息得到客户code
		nb_cus cus = cusService.queryCusByKey(custormerKey);
		//根据客户id查询水表的历史抄表记录
		String cusCode = cus.getId();
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("customerId",cusCode);
		mapPar.put("watermetercode",imei);
		mapPar.put("startDate",startDate);
		mapPar.put("endDate",endDate);
		List<nb_meterread_log> rLogList = meterreadlogService.queryWMLatestNumCusId(mapPar);
		if(rLogList.size() != 0){
			m = new Message("40000","success",rLogList);
		}else{
			m = new Message("40001","fail","");
		}

		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

	/**
	 * 
	* @Title: QueryWMControl 
	* @Description: 查询水表当前开关阀状态
	* @param: @param imei 水表唯一号
	* @param: @param custormerKey 客户秘钥
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:17:28  
	 */
	@GetMapping(value="/QueryWMControl")
	public JSONObject QueryWMControl(String imei,String custormerKey){
		Message m = new Message("50002","error","");
		//查询秘钥查询客户信息得到客户code
		nb_cus cus = cusService.queryCusByKey(custormerKey);
		//根据客户id查询水表的阀控状态
		String cusCode = cus.getId();
		Map<String, String> mapPar = new HashMap<String, String>();
		mapPar.put("customerId",cusCode);
		mapPar.put("watermetercode",imei);
		List<WMControl> wList = equipmentService.queryWMControlCusId(mapPar);
		if(wList.size() != 0){
			m = new Message("50000","success",wList);
		}else{
			m = new Message("50001","fail","");
		}

		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

	/**
	 * 
	* @Title: OptionWMCommand 
	* @Description: 缓存命令
	* @param: @param imei 水表唯一号
	* @param: @param custormerKey 客户秘钥
	* @param: @param commandType 命令类型
	* @param: @param command 命令体
	* @return: JSONObject    
	* @Date: 2019年7月25日 下午3:18:04  
	 */
	@GetMapping(value="/OptionWMCommand")
	public JSONObject OptionWMCommand(String imei,String custormerKey,String commandType,String command){
		Message m = new Message("60002","error","");
		try {
			//查询秘钥查询客户信息得到客户code
			nb_cus cus = cusService.queryCusByKey(custormerKey);
			//根据客户idx下发水表命令
			String cusCode = cus.getId();
			//查询水表信息类型
			Map<String, String> mapPar = new HashMap<String, String>();
			mapPar.put("customerId",cusCode);
			mapPar.put("watermetercode",imei);
			nb_watermeter_dict dict = watermeterService.queryWMTypeCusId(mapPar);
			String deviceId = dict.getCode();
			if(nbIotConfig.getValve_control().equals(commandType)){
				if("0".equals(dict.getMagnetism())){
					m = new Message("60003","error","");
				}else{
					if("0".equals(dict.getControl())){
						m = new Message("60004","error","");
					}else{
						//下发阀控命令
						signalDeliveryService.optionWMJedis(deviceId,nbIotConfig.getValve_control(),Integer.valueOf(command));
						m = new Message("60000","success",deviceId);
					}
				}
			}
			if(nbIotConfig.getDelivery().equals(commandType)){
				String times = "900";
				if(!"0".equals(command)){
					double a = Double.valueOf(command) * 3600;
					int b = (int) a;
					times = String.valueOf(b);
				}
				signalDeliveryService.reportCycleJedis(deviceId,times,nbIotConfig.getDelivery());
				m = new Message("60000","success",deviceId);
			}
			if(nbIotConfig.getWater_meter_basic().equals(commandType)){
				if("1".equals(dict.getMagnetism())){
					signalDeliveryService.readBasicNumHACJedis(deviceId,command,nbIotConfig.getWater_meter_basic());
				}
				if("0".equals(dict.getMagnetism())){
					signalDeliveryService.readBasicNumTLVJedis(deviceId,command,"SETRAW");
				}
				m = new Message("60000","success",deviceId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			m = new Message("60002","error","");
		}
		JSONObject jsonObject=JSONObject.fromObject(m);
		return jsonObject;
	}

}