package com.ets.system.sysEquipmentAlarm.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.sysEquipmentAlarm.entity.tb_sys_equipment_alarm;



/**
 * 设备告警
 * @author WH
 *
 */
public interface SysEquipmentAlarmDao {

	List<tb_sys_equipment_alarm> selectSysAlarm(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	void insertSysAlarm(tb_sys_equipment_alarm endity);

	tb_sys_equipment_alarm infoSysAlarm(Map<String, Object> map);

}
