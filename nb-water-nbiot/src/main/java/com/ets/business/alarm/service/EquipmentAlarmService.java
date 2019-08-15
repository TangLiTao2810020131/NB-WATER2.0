package com.ets.business.alarm.service;

import com.ets.business.alarm.dao.EquipmentAlarmDao;
import com.ets.business.alarm.entity.nb_equipment_alarm;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.owner.dao.OwnerDao;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.nb_iot.model.DeviceInfo;
import com.ets.nb_iot.model.ValveControl;
import com.ets.nb_iot.model.WaterMeterBasic;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName:     EquipmentAlarmService.java 
 * @Description:   设备告警业务类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:15:08
 */
@Service
@Transactional
public class EquipmentAlarmService {
	
	@Resource
	EquipmentAlarmDao alarmDao;
	
	@Resource
	OwnerDao ownerDao;

	/**
	 * 
	* @Title: opentionAlarm 
	* @Description: 新增告警记录
	* @param: @param alarm 告警对象
	* @return: void    
	* @Date: 2019年7月25日 下午6:17:21  
	 */
	public void AddAlarm(nb_equipment_alarm alarm) {
		try {
			alarm.setId(UUIDUtils.getUUID());
			alarm.setCtime(DateTimeUtils.getnowdate());
			alarmDao.insertAlarm(alarm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 
	* @Title: addEquipmentAlarm 
	* @Description: 根据上报来的数据，添加告警日志
	* @param: @param equipment 设备详情
	* @param: @param basic 设备基础数据
	* @param: @param info 设备信息
	* @param: @param value  阀控信息
	* @return: void    
	* @Date: 2019年7月25日 下午5:20:47  
	 */
	public void addEquipmentAlarm(nb_watermeter_equipment equipment, WaterMeterBasic basic, DeviceInfo info,ValveControl value) {

		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("customercode", equipment.getCustomercode());
		map.put("doornumid", equipment.getDoornumid());
		OwnerModel oModel = ownerDao.ownerInfo(map);
		
		if(oModel != null){
			nb_equipment_alarm alarm = new nb_equipment_alarm();
			
			String status1 = info.getBatteryStatus();//电池状态
			String status2 = basic.getMeasurementfaultStatus();//计量错误状态
			String status3 = value.getValvefaultStatus();//阀门故障状态
			
			if(!"0".equals(status1)){
				alarm = new nb_equipment_alarm();
				alarm.setUseraccount(oModel.getUseraccount());
				alarm.setUsername(oModel.getUsername());
				alarm.setWatermetercode(equipment.getWatermetercode());
				alarm.setCustomercode(equipment.getCustomercode());
				alarm.setAddress(oModel.getAddress());
				alarm.setAlarmtime(DateTimeUtils.getnowdate());
				alarm.setAlarmstatus("BS-" + status1);
				alarm.setAlarmtype("电池告警");
				AddAlarm(alarm);
			}
			if(!"0".equals(status2)){
				alarm = new nb_equipment_alarm();
				alarm.setUseraccount(oModel.getUseraccount());
				alarm.setUsername(oModel.getUsername());
				alarm.setWatermetercode(equipment.getWatermetercode());
				alarm.setCustomercode(equipment.getCustomercode());
				alarm.setAddress(oModel.getAddress());
				alarm.setAlarmtime(DateTimeUtils.getnowdate());
				alarm.setAlarmstatus("MFS-" + status2);
				alarm.setAlarmtype("计量错误状态");
				AddAlarm(alarm);
			}
			
			if(!"0".equals(status3)){
				alarm = new nb_equipment_alarm();
				alarm.setUseraccount(oModel.getUseraccount());
				alarm.setUsername(oModel.getUsername());
				alarm.setWatermetercode(equipment.getWatermetercode());
				alarm.setCustomercode(equipment.getCustomercode());
				alarm.setAddress(oModel.getAddress());
				alarm.setAlarmtime(DateTimeUtils.getnowdate());
				alarm.setAlarmstatus("RSOI-" + status3);
				alarm.setAlarmtype("阀门告警");
				AddAlarm(alarm);
			}
		}
	}

	/**
	 * 
	* @Title: addBaseNumAlarm 
	* @Description: 计量错误告警
	* @param: @param equipment  设备详情  
	* @return: void    
	* @Date: 2019年7月25日 下午5:35:47  
	 */
	public void addBaseNumAlarm(nb_watermeter_equipment equipment) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("customercode", equipment.getCustomercode());
		map.put("doornumid", equipment.getDoornumid());
		OwnerModel oModel = ownerDao.ownerInfo(map);
		
		if(oModel != null){
			nb_equipment_alarm alarm = new nb_equipment_alarm();;
			
			alarm.setUseraccount(oModel.getUseraccount());
			alarm.setUsername(oModel.getUsername());
			alarm.setWatermetercode(equipment.getWatermetercode());
			alarm.setCustomercode(equipment.getCustomercode());
			alarm.setAddress(oModel.getAddress());
			alarm.setAlarmtime(DateTimeUtils.getnowdate());
			alarm.setAlarmstatus("MFS-1");
			alarm.setAlarmtype("计量数据错误告警");
			AddAlarm(alarm);
		}
	}
}
