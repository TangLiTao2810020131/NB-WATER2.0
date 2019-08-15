package com.ets.system.sysReadLog.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ets.business.equipment.entity.nb_watermeter_equipment;
import com.ets.common.DateTimeUtils;
import com.ets.common.UUIDUtils;
import com.ets.nb_iot.model.ReceiveEquipment;
import com.ets.system.sysEquipment.entity.tb_sys_equipment;
import com.ets.system.sysReadLog.dao.SysReadLogDao;
import com.ets.system.sysReadLog.entity.tb_sys_read_log;

@Service
@Transactional
public class SysReadLogService {
	
	@Resource
	SysReadLogDao sysReadLogDao;
	
	public void addSysReadLog(tb_sys_read_log entity){
		try {
			sysReadLogDao.insertSysReadLog(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<tb_sys_read_log> getReadLog(Map<String, Object> map) {
		try {
			return sysReadLogDao.selectSysReadLogs(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long getCount(Map<String, Object> map) {
		try {
			return sysReadLogDao.selectCount(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public tb_sys_read_log info(Map<String, Object> map) {
		try {
			return sysReadLogDao.info(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	* @Title: addSysReadLog 
	* @Description: 接收到上报数据 存入数据库
	* @param: @param date 数据上次字符串
	* @param: @param equipmentReceive 设备信息
	* @param: @param equipment 设备详情
	* @param: @param ip 地址
	* @param: @param baseNum 表读数
	* @return: void    
	* @Date: 2019年7月25日 下午5:43:00  
	 */
	public void addSysReadLog(String date,ReceiveEquipment equipmentReceive,tb_sys_equipment equipment,String ip,String baseNum){
		tb_sys_read_log entity = new tb_sys_read_log();
		entity.setId(UUIDUtils.getUUID());
		entity.setImei(equipment.getImei());
		entity.setCtime(DateTimeUtils.getnowdate());
		entity.setContent(date);
		entity.setDeviceId(equipmentReceive.getDeviceId());
		entity.setBaseNum(baseNum);
		entity.setIpaddress(ip);
		addSysReadLog(entity);
	}

}
