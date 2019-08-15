package com.ets.system.sysEquipment.dao;

import java.util.List;
import java.util.Map;

import com.ets.system.sysEquipment.entity.tb_sys_equipment;

/**
 * 水表设备连接数据库dao类
 * @author Administrator
 *
 */
public interface SysEquipmentDao {

	List<tb_sys_equipment> selectSysEquipment(Map<String, Object> map);

	long selectCount(Map<String, Object> map);

	void insertSysEquipment(tb_sys_equipment equipment);

	void updateSysEquipment(tb_sys_equipment equipment);

	void deleteSysEquipment(String[] id);

	int isCheckIMEI(String imei);

	void updateSysEquipment(String deviceid);

	tb_sys_equipment selectSysWMEinfoByDeviceId(String deviceid);

	void close(String deviceId);

	void open(String deviceId);

	void updateCstatus(String deviceId);

	void updateDstatus(String deviceId);

	int selectWaterNum(String batchid);
}
