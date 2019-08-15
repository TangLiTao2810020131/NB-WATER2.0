package com.ets.system.sysEquipmentAlarm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.system.sysEquipmentAlarm.dao.SysEquipmentAlarmDao;
import com.ets.system.sysEquipmentAlarm.entity.tb_sys_equipment_alarm;

/**
 * 社保告警
 * @author wuhao
 *
 */
@Service
@Transactional
public class SysEquipmentAlarmService {
	
	@Resource
	SysEquipmentAlarmDao sysAlarmDao;


	public List<tb_sys_equipment_alarm> getAlarm(Map<String, Object> map) {
		try {
			return sysAlarmDao.selectSysAlarm(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return sysAlarmDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public void opentionSysAlarm(tb_sys_equipment_alarm alarm) {
		try {
			alarm.setId(UUIDUtils.getUUID());
			alarm.setCtime(DateTimeUtils.getnowdate());
			sysAlarmDao.insertSysAlarm(alarm);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public tb_sys_equipment_alarm infoSysAlarm(Map<String, Object> map) {
		try {
			return sysAlarmDao.infoSysAlarm(map);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


}
