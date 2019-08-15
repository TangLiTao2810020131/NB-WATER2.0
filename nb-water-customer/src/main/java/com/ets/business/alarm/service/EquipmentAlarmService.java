package com.ets.business.alarm.service;

import com.ets.business.alarm.dao.EquipmentAlarmDao;
import com.ets.business.alarm.entity.nb_equipment_alarm;
import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.business.owner.dao.OwnerDao;
import com.ets.business.owner.entity.OwnerModel;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 社保告警
 * @author wuhao
 *
 */
@Service
@Transactional
public class EquipmentAlarmService {
	
	@Resource
	EquipmentAlarmDao alarmDao;
	
	@Resource
	OwnerDao ownerDao;

	public List<nb_equipment_alarm> getAlarm(Map<String, Object> map) {
		try {
			return alarmDao.selectAlarm(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return alarmDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionAlarm(nb_equipment_alarm alarm) {
		try {
			alarm.setId(UUIDUtils.getUUID());
			alarm.setCtime(DateTimeUtils.getnowdate());
			alarmDao.insertAlarm(alarm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public nb_equipment_alarm infoAlarm(Map<String, Object> map) {
		try {
			return alarmDao.infoAlarm(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
