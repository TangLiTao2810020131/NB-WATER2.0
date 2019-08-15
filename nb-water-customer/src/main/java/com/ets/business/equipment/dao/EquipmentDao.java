package com.ets.business.equipment.dao;

import com.ets.business.equipment.entity.EquipmentModel;
import com.ets.business.equipment.entity.WMControl;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.record.waterchange.entity.nb_watermeter_changerecord;

import java.util.List;
import java.util.Map;

/**
 * 水表设备连接数据库dao类
 * @author Administrator
 *
 */
public interface EquipmentDao {


	List<nb_watermeter_equipment> selectEquipment(Map<String, Object> map);


	long selectCount(Map<String, Object> map);

	void updateEquipment(nb_watermeter_equipment equipment);

	void insertEquipment(nb_watermeter_equipment equipment);

	nb_watermeter_equipment infoEquipment(Map<String, Object> map);

	void deleteEquipment(String[] id);

	List<nb_watermeter_equipment> infoEquipmentList(String[] id);


	List<nb_watermeter_equipment> selectEquipmentList(String customerId);


	EquipmentModel infoEquipmentM(Map<String, Object> map);


	nb_watermeter_changerecord infoWaterchange(Map<String, Object> map);

	int isCheckIMEI(Map<String, Object> map);
	
	nb_watermeter_equipment selectWMEinfoByDeviceId(String deviceid);


	List<nb_watermeter_equipment> selectEquipmentDoorId(Map<String, String> map);


	List<nb_watermeter_equipment> selectWaterMeterCusId(Map<String, String> map);
	List<WMControl> selectWMControlCusId(Map<String, String> map);


	int selectWaterNum(Map<String, Object> map);


	void updateOnlinePRS(nb_watermeter_equipment equipment);


	void updateOnline(nb_watermeter_equipment equipment);
	
	
}
