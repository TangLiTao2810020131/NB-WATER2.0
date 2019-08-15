package com.ets.business.alarm.dao;

import com.ets.business.alarm.entity.nb_equipment_alarm;

import java.util.List;
import java.util.Map;


/**
 * 设备告警
 * @author WH
 *
 */
public interface EquipmentAlarmDao {

	List<nb_equipment_alarm> selectAlarm(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	void insertAlarm(nb_equipment_alarm endity);

	nb_equipment_alarm infoAlarm(Map<String, Object> map);

}
