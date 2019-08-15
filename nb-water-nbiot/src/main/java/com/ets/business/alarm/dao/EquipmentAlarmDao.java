package com.ets.business.alarm.dao;

import com.ets.business.alarm.entity.nb_equipment_alarm;

/**
 * 
 * @ClassName:     EquipmentAlarmDao.java 
 * @Description:   设备告警数据库操作类
 * @author         吴浩
 * @version        nb-water2.0   
 * @Date           2019年7月25日 下午6:20:41
 */
public interface EquipmentAlarmDao {

	/**
	 * 
	* @Title: insertAlarm 
	* @Description: 新增告警记录
	* @param: @param endity 告警实体对象
	* @return: void    
	* @Date: 2019年7月25日 下午6:25:54  
	 */
	void insertAlarm(nb_equipment_alarm endity);
}
